package com.fractals.controllers;

import com.fractals.beans.OrderItem;
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
     * Finds all tracks that have never been sold.
     *
     * @return
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

        List<OrderItem> oItems = new ArrayList<>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Track> mainQuery = cb.createQuery(Track.class);
        Root<Track> mainRoot = mainQuery.from(Track.class);
        
        CriteriaQuery<OrderItem> c = cb.createQuery(OrderItem.class);        
        Root<OrderItem> orderI = c.from(OrderItem.class);

        // Subquery logic.
        Subquery<OrderItem> subQuery =  mainQuery.subquery(OrderItem.class);
        Root<OrderItem> subRoot = subQuery.from(OrderItem.class);
        Join orderJoin  = subRoot.join("order");
        subQuery.where(cb.between(orderJoin.<LocalDateTime>get("orderDate"), start, end));
        
        // Main query
        mainRoot.join(subQuery, JoinType.LEFT);
        
        Join orderJ = orderI.join("order");
        c.where(cb.between(orderJ.<LocalDateTime>get("orderDate"), start, end));
        
        TypedQuery<OrderItem> tqOI = entityManager.createQuery(c);
        oItems = (List<OrderItem>) tqOI.getResultList();
        

        System.out.println(oItems);
        
        return null;
    }

}
