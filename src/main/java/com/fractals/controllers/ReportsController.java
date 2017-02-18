package com.fractals.controllers;

//import com.fractals.beans.Order;
//import com.fractals.beans.OrderItem;
//import com.fractals.beans.OrderItem_;
//import com.fractals.beans.Order_;
//import com.fractals.beans.Track;
//import com.fractals.beans.User;
import java.io.Serializable;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Named;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Tuple;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.Root;
//import javax.persistence.criteria.Subquery;
//import javax.transaction.UserTransaction;
//
///**
// * This controller provides basic querying to generate reports about the
// * database and then display the generated data on a page.
// *
// * @author Danieil Skrinikov
// * @version 0.0.01
// * @since 2017-02-09
// */
//@Named("reportsController")
//@RequestScoped
public class ReportsController implements Serializable {
//
//    @Resource
//    private UserTransaction userTransaction;
//
//    @PersistenceContext(unitName = "fractalsPU")
//    private EntityManager entityManager;
//
//    // Queries
//    private static final String ZERO_USERS = "SELECT u FROM User u LEFT JOIN u.orders o WHERE o.user IS NULL";
//    //private static final String ZERO_TRACKS = "SELECT t FROM Track t LEFT JOIN t.orderItems p LEFT JOIN p.order o WHERE p.track IS NULL AND o.orderDate <= ?1 AND o.orderDate>= ?2";
//    private static final String ZERO_TRACKS = "SELECT t FROM Track t LEFT JOIN ( SELECT p FROM OrderItem p JOIN p.Order o WHERE o.orderDate >= :start AND o.orderDate <= :end ) x ON t.id = x.track WHERE x.track IS NULL";
//    private String s = "SELECT p FROM OrderItem p JOIN p.order o WHERE o.orderDate >= :start AND o.orderDate <= :end";
//
//    /**
//     * Finds all users who have never made a purchase and returns a list with
//     * their entity objects.
//     *
//     * @return List of User entities who have never made a purchase.
//     */
//    public List<User> getZeroClients() {
//        List<User> users = new ArrayList<>();
//
//        TypedQuery<User> query = entityManager.createQuery(ZERO_USERS, User.class);
//        users = query.getResultList();
//
//        return users;
//    }
//
//    /**
//     * Finds all tracks that have never been sold between the given time range.
//     *
//     * @return List of all the Tracks that never been sold.
//     */
//    public List<Track> getZeroTracks(LocalDateTime start, LocalDateTime end) {
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<Track> mainQuery = cb.createQuery(Track.class);
//        Root<Track> mainRoot = mainQuery.from(Track.class);
//
//        // Subquery logic.
//        Subquery<Track> subQuery = mainQuery.subquery(Track.class);
//        Root<Track> subRoot = subQuery.from(Track.class);
//        Join orderJoin = subRoot.join("orderItems").join("order");
//        subQuery.select(subRoot.get("id"));
//        subQuery.where(cb.between(orderJoin.<LocalDateTime>get("orderDate"), start, end)).distinct(true);
//
//        // Main query
//        mainQuery.select(mainRoot);
//        mainQuery.where(mainRoot.get("id").in(subQuery)).distinct(true);
//
//        return entityManager.createQuery(mainQuery).getResultList();
//    }
//
//    /**
//     * Fetches the top clients from the database between the given dates.
//     * Returns a list which contains all clients who have made a purchase. Each
//     * list item contains the client's id, username and the total purchase
//     * amount.
//     *
//     * @param start Start of the date range period.
//     * @param end End of the date range period.
//     * @return List ordered in descending order of all the clients based on
//     * their purchase amount.
//     */
//    public List<Object[]> getTopClients(LocalDateTime start, LocalDateTime end) {
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        // Query
//        // SELECT username, SUM(gross_cost) FROM orders INNER JOIN users ON orders.user_id=users.id GROUP BY user_id ORDER BY SUM(gross_cost) DESC;
//        CriteriaQuery<Tuple> query = cb.createTupleQuery();
//        Root<Order> root = query.from(Order.class);
//        Join userJoin = root.join(Order_.user);
//        query.multiselect(cb.sum(root.get("grossCost")));
//        query.where(cb.between(root.<LocalDateTime>get("orderDate"), start, end));
//        query.groupBy(userJoin.get("id"));
//        query.orderBy(cb.desc(cb.sum(root.get("grossCost"))));
//
//        List<Tuple> result = entityManager.createQuery(query).getResultList();
//        List<Object[]> convResult = new ArrayList<>();
//        
//        System.out.println(result.size());
//        
//        // Converting Tuple to an object array to facilitate data transmission.
//        for (Tuple t : result) {
//            convResult.add(new Object[]{t.get(0), t.get(1)});
//        }
//        
//        System.out.println(result.size());
//        return convResult;
//    }
//
//    /**
//     *
//     * @param start
//     * @param end
//     * @return
//     */
//    public List<Object[]> getTopTrackSellers(LocalDateTime start, LocalDateTime end) {
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<Tuple> query = cb.createTupleQuery();
//        Root<OrderItem> root = query.from(OrderItem.class);
//        Join track = root.join("track");
//        Join order = root.join("order");
//        query.multiselect(track.get("title"), cb.count(root.get("track")));
//        query.groupBy(root.get("track"));
//        query.where(cb.and(cb.between(order.<LocalDateTime>get("orderDate"), start, end)));
//
//        return null;
//    }
//
//    /**
//     *
//     * @param start
//     * @param end
//     * @return
//     */
//    public List<Object[]> getTopAlbumSellers(LocalDateTime start, LocalDateTime end) {
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<Tuple> query = cb.createTupleQuery();
//        Root<OrderItem> root = query.from(OrderItem.class);
//        Join album = root.join("album");
//        Join order = root.join("order");
//        query.multiselect(cb.count(root.get("album")));
//        query.groupBy(root.get("album"));
//        query.where(cb.and(cb.between(order.<LocalDateTime>get("orderDate"), start, end)));
//
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    /**
//     * Returns all the orders of a given client in a list.
//     *
//     * @param identifier Can be the username of the client or the email.
//     * @param start Start of the date range period.
//     * @param end End of the date range period.
//     * @return list of orders made by the client ordered by date in descending
//     * order.
//     */
//    public List<Order> getSalesByClient(String identifier, LocalDateTime start, LocalDateTime end) {
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<Order> query = cb.createQuery(Order.class);
//        Root<Order> root = query.from(Order.class);
//        Join user = root.join(Order_.user);
//        query.select(root);
//
//        // Can be either email.
//        query.where(
//                cb.and(
//                        cb.between(root.<LocalDateTime>get("orderDate"), start, end),
//                        cb.or(
//                                cb.equal(user.get("username"), identifier),
//                                cb.equal(user.get("email"), identifier)
//                        )
//                )
//        );
//
//        return entityManager.createQuery(query).getResultList();
//    }
//
//    /**
//     * Returns a list of all the orders made between the given date range.
//     * 
//     * @param start Start of the date range period.
//     * @param end End of the date range period.
//     * @return list of orders.
//     */
//    public List<Order> getTotalSales(LocalDateTime start, LocalDateTime end) {
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        
//        CriteriaQuery<Order> query = cb.createQuery(Order.class);
//        Root<Order> root = query.from(Order.class);
//        query.select(root);
//        query.where(cb.and(cb.between(root.<LocalDateTime>get("orderDate"), start, end)));
//        query.orderBy(cb.desc(root.get(Order_.orderDate)));
//
//        return entityManager.createQuery(query).getResultList();
//    }
//
//    /**
//     * Returns a list of order items which matched with the given track id.
//     *
//     * @param trackId primary key of the track to report for.
//     * @param start Start of the date range period.
//     * @param end End of the date range period.
//     * @return list of order items which matched with the id.
//     */
//    public List<OrderItem> getSalesByTrack(int trackId, LocalDateTime start, LocalDateTime end) {
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<OrderItem> query = cb.createQuery(OrderItem.class);
//        Root<OrderItem> root = query.from(OrderItem.class);
//        Join orders = root.join(OrderItem_.order);
//        query.select(root);
//        query.orderBy(cb.desc(orders.get(Order_.orderDate)));
//        query.where(cb.and(cb.equal(root.get(OrderItem_.track), trackId), cb.between(orders.<LocalDateTime>get("orderDate"), start, end)));
//
//        return entityManager.createQuery(query).getResultList();
//    }
//    
//    /**
//     * Fetches all the instances of the given album id in the database's orders
//     * table between the specified date ranges.
//     * 
//     * @param albumId primary key of the album to search for.
//     * @param start Start of the date range period.
//     * @param end End of the date range period.
//     * @return list of order items which have the album in their result.
//     */
//    public List<OrderItem> getSalesByAlbum(int albumId, LocalDateTime start, LocalDateTime end){
//        if (start == null || end == null) {
//            return null;
//        }
//
//        if (start.isAfter(end)) {
//            LocalDateTime temp = start;
//            start = end;
//            end = temp;
//        }
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<OrderItem> query = cb.createQuery(OrderItem.class);
//        Root<OrderItem> root = query.from(OrderItem.class);
//        Join orders = root.join(OrderItem_.order);
//        query.select(root);
//        query.orderBy(cb.desc(orders.get(Order_.orderDate)));
//        query.where(cb.and(cb.equal(root.get(OrderItem_.track), albumId), cb.between(orders.<LocalDateTime>get("orderDate"), start, end)));
//
//        return entityManager.createQuery(query).getResultList();
//    }
}
