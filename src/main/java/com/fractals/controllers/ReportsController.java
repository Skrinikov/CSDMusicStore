package com.fractals.controllers;

import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.OrderItem_;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;

/**
 * This controller provides basic querying to generate reports about the
 * database and then display the generated data on a page.
 *
 * @author Danieil Skrinikov
 * @version 0.0.01
 * @since 2017-02-09
 */
@Named("reportsController")
@RequestScoped
public class ReportsController implements Serializable {

    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;

    // Queries
    private static final String ZERO_USERS = "SELECT u FROM User u LEFT JOIN u.orders o WHERE o.user IS NULL";
    //private static final String ZERO_TRACKS = "SELECT t FROM Track t LEFT JOIN t.orderItems p LEFT JOIN p.order o WHERE p.track IS NULL AND o.orderDate <= ?1 AND o.orderDate>= ?2";
    private static final String ZERO_TRACKS = "SELECT t FROM Track t LEFT JOIN ( SELECT p FROM OrderItem p JOIN p.Order o WHERE o.orderDate >= :start AND o.orderDate <= :end ) x ON t.id = x.track WHERE x.track IS NULL";
    private String s = "SELECT p FROM OrderItem p JOIN p.order o WHERE o.orderDate >= :start AND o.orderDate <= :end";

    /**
     * Finds all users who have never made a purchase and returns a list with
     * their entity objects.
     *
     * @return List of User entities who have never made a purchase.
     */
    public List<User> getZeroClients() {
        List<User> users = new ArrayList<>();

        TypedQuery<User> query = entityManager.createQuery(ZERO_USERS, User.class);
        users = query.getResultList();

        return users;
    }

    /**
     * Finds all tracks that have never been sold between the given time range.
     *
     * @return List of all the Tracks that never been sold.
     */
    public List<Track> getZeroTracks(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Track> mainQuery = cb.createQuery(Track.class);
        Root<Track> mainRoot = mainQuery.from(Track.class);        

        // Subquery logic.
        Subquery<Track> subQuery =  mainQuery.subquery(Track.class);
        Root<Track> subRoot = subQuery.from(Track.class);
        Join orderJoin  = subRoot.join("orderItems").join("order");
        subQuery.select(subRoot.get("id"));
        subQuery.where(cb.between(orderJoin.<LocalDateTime>get("orderDate"), start, end)).distinct(true);
        
        // Main query
        mainQuery.select(mainRoot);
        mainQuery.where(mainRoot.get("id").in(subQuery)).distinct(true);     
        
        return entityManager.createQuery(mainQuery).getResultList();
    }
    
    /**
     * Fetches the top clients from the database between the given dates. Returns
     * a list which contains all clients who have made a purchase. Each list item
     * contains the client's id, username and the total purchase amount.
     * 
     * @param start Start of the date range period.
     * @param end End of the date range period.
     * @return List ordered in descending order of all the clients based on their purchase amount.
     */
    public int getTopClients(LocalDateTime start, LocalDateTime end){
        if (start == null || end == null) {
            return 0;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        // Query
        // SELECT username, SUM(gross_cost) FROM orders INNER JOIN users ON orders.user_id=users.id GROUP BY user_id ORDER BY SUM(gross_cost) DESC;
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Order> root = query.from(Order.class);
        Join userJoin = root.join("user");
        query.multiselect(cb.sum(root.get("grossCost")),userJoin.get("username"));
        query.where(cb.between(root.<LocalDateTime>get("orderDate"), start, end));
        query.groupBy(userJoin.get("id"));
        query.orderBy(cb.desc(cb.sum(root.get("grossCost"))));
        
        List<Tuple> result = entityManager.createQuery(query).getResultList();
        List<Object[]> convResult = new ArrayList<>();
        
        // Converting Tuple to an object array to facilitate data transmission.
        for(Tuple t : result){
            convResult.add(new Object[]{t.get(0),t.get(1)});
        }
        
        return result.size();
    }
    
    /**
     * 
     * @param start
     * @param end
     * @return 
     */
    public List<Object[]> getTopTrackSellers(LocalDateTime start, LocalDateTime end){
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<OrderItem> root = query.from(OrderItem.class);
        Join tack = root.join(OrderItem_.track);
        Join order = root.join(OrderItem_.order);
        query.multiselect(cb.count(root.get(OrderItem_.track)));
        query.groupBy(root.get(OrderItem_.track));
        query.where(cb.and(cb.between(root.<LocalDateTime>get("orderDate"), start, end)));
        
        return null;
    }

}
