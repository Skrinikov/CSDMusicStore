package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.lang.Long;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.UserTransaction;

/**
 * This controller provides basic querying to generate reports about the
 * database and then display the generated data on a page.
 *
 * @author Danieil Skrinikov
 * @version 0.0.01
 * @since 2017-02-09
 */
@Named("reportsController")
@SessionScoped
public class ReportsController implements Serializable {

    private static final Logger log = Logger.getLogger("ReportsController.class");

    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;

    // Queries
    private static final String ZERO_USERS = "SELECT u FROM User u LEFT JOIN u.orders o WHERE o.user IS NULL";

    /**
     * Finds all users who have never made a purchase and returns a list with
     * their entity objects.
     *
     * @return List of User entities who have never made a purchase.
     */
    public List<User> getZeroClients(LocalDateTime start, LocalDateTime end) {
        List<User> users = new ArrayList<>();

        TypedQuery<User> query = entityManager.createQuery(ZERO_USERS, User.class);
        users = query.getResultList();
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Main Query
        CriteriaQuery<User> mainQuery = cb.createQuery(User.class);
        Root<User> mainRoot = mainQuery.from(User.class);
        Join join = mainRoot.join("orders", JoinType.LEFT);
        mainQuery.select(mainRoot).where(cb.isNull(join.get("user")));

        return entityManager.createQuery(mainQuery).getResultList();

    }

    /**
     * Finds all tracks that have never been sold between the given time range.
     *
     * @param start
     * @param end
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
        Subquery<Track> subQuery = mainQuery.subquery(Track.class);
        Root<Track> subRoot = subQuery.from(Track.class);
        Join orderJoin = subRoot.join("orderItems").join("order");
        subQuery.select(subRoot.get("id"));
        subQuery.where(cb.between(orderJoin.<LocalDateTime>get("orderDate"), start, end)).distinct(true);

        // Main query
        mainQuery.select(mainRoot);
        mainQuery.where(mainRoot.get("id").in(subQuery)).distinct(true);

        return entityManager.createQuery(mainQuery).getResultList();
    }

    /**
     * Fetches the top clients from the database between the given dates.
     * Returns a list which contains all clients who have made a purchase. Each
     * list item contains the client's id, username and the total purchase
     * amount.
     *
     * @param start Start of the date range period.
     * @param end End of the date range period.
     * @return List ordered in descending order of all the clients based on
     * their purchase amount.
     */
    public List<User> getTopClients(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Query
        // SELECT username, SUM(gross_cost) FROM orders INNER JOIN users ON orders.user_id=users.id GROUP BY user_id ORDER BY SUM(gross_cost) DESC;
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Join join = root.join("orders");
        query.multiselect(root);
        query.where(cb.between(join.<LocalDateTime>get("orderDate"), start, end));
        //query.orderBy(cb.desc(cb.count(root.get("orders"))));   
        query.groupBy(join.get("user"));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Selects top tracks
     *
     * @param start
     * @param end
     * @return
     */
    public List<Track> getTopTrackSellers(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<Track> root = query.from(Track.class);
        Join items = root.join("orderItems");
        Join order = items.join("order");
        query.select(root);
        query.groupBy(items.get("track"));
        query.where(cb.and(cb.between(order.<LocalDateTime>get("orderDate"), start, end)));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public List<Album> getTopAlbumSellers(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }     

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> root = query.from(Album.class);
        Join items = root.join("orderItems");
        Join order = items.join("order");
        query.select(root);
        query.groupBy(items.get("album"));
        query.where(cb.and(cb.between(order.<LocalDateTime>get("orderDate"), start, end)));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Returns all the orders of a given client in a list.
     *
     * @param identifier Can be the username of the client or the email.
     * @param start Start of the date range period.
     * @param end End of the date range period.
     * @return list of orders made by the client ordered by date in descending
     * order.
     */
    public List<Order> getSalesByClient(String identifier, LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Join user = root.join("user");
        query.select(root);

        // Can be either email.
        query.where(
                cb.and(
                        cb.between(root.<LocalDateTime>get("orderDate"), start, end),
                        cb.or(
                                cb.equal(user.get("username"), identifier),
                                cb.equal(user.get("email"), identifier)
                        )
                )
        );

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Returns a list of all the orders made between the given date range.
     *
     * @param start Start of the date range period.
     * @param end End of the date range period.
     * @return list of orders.
     */
    public List<Order> getTotalSales(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        query.where(cb.and(cb.between(root.<LocalDateTime>get("orderDate"), start, end)));
        query.orderBy(cb.desc(root.get("orderDate")));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Returns a list of order items which matched with the given track id.
     *
     * @param trackId primary key of the track to report for.
     * @param start Start of the date range period.
     * @param end End of the date range period.
     * @return list of order items which matched with the id.
     */
    public List<OrderItem> getSalesByTrack(int trackId, LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<OrderItem> query = cb.createQuery(OrderItem.class);
        Root<OrderItem> root = query.from(OrderItem.class);
        Join orders = root.join("order");
        query.select(root);
        query.orderBy(cb.desc(orders.get("orderDate")));
        query.where(cb.and(cb.equal(root.get("track").get("id"), trackId), cb.between(orders.<LocalDateTime>get("orderDate"), start, end)));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Fetches all the instances of the given album id in the database's orders
     * table between the specified date ranges.
     *
     * @param albumId primary key of the album to search for.
     * @param start Start of the date range period.
     * @param end End of the date range period.
     * @return list of order items which have the album in their result.
     */
    public List<OrderItem> getSalesByAlbum(int albumId, LocalDateTime start, LocalDateTime end) {
        log.info("getSalesByAlbum " + albumId);
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<OrderItem> query = cb.createQuery(OrderItem.class);
        Root<OrderItem> root = query.from(OrderItem.class);
        Join orders = root.join("order");
        query.select(root);

        query.where(cb.and(cb.equal(root.get("album").get("id"), albumId), cb.between(orders.<LocalDateTime>get("orderDate"), start, end)));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Gets all OrderItems where the artist id matches the given id.
     *
     * @param artistId Id of the artist
     * @param start start of the range period
     * @param end end of the date range period.
     * @return list of order items.
     */
    public List<OrderItem> getSalesByArtist(int artistId, LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<OrderItem> query = cb.createQuery(OrderItem.class);
        Root<OrderItem> root = query.from(OrderItem.class);
        Join order = root.join("order");
        Join artist = root.join("track").join("artist");
        query.select(root);
        query.orderBy(cb.desc(order.get("orderDate")));
        query.where(cb.and(cb.equal(artist.get("id"), artistId), cb.between(order.<LocalDateTime>get("orderDate"), start, end)));

        return entityManager.createQuery(query).getResultList();
    }

    //-------------------------------------------------------------------------------------------------------------------------------
    /**
     * Gets the number of users.
     *
     * @return
     */
    public int getVisitorCount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList().size();
    }

    public Long getSalesCount(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0L;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        query.select(cb.count(root.get("id")));
        query.where(cb.between(root.<LocalDateTime>get("orderDate"), start, end));
        long l = entityManager.createQuery(query).getSingleResult();
        log.info(l + "");
        return l;
    }

    /**
     * Selects the total amount of revenue between the given date.
     *
     * @param start
     * @param end
     * @return
     */
    public double getSalesAmount(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = cb.createQuery(Double.class);
        Root<Order> root = query.from(Order.class);
        query.select(cb.sumAsDouble(root.get("netCost")));
        query.where(cb.between(root.<LocalDateTime>get("orderDate"), start, end));
        log.info("Went here");

        // Because JPA throws NullPointerException if single result is not found.
        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * Returns the total cost of all the orders within the given date range.
     *
     * @param start start of the date range period.
     * @param end end of the date range period.
     * @return total cost.
     */
    public double getTotalCost(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        List<Order> orders = getTotalSales(start, end);
        double sum = 0;

        for (Order o : orders) {
            for (OrderItem item : o.getOrderItems()) {
                if (item.getTrack() != null) {
                    sum += item.getTrack().getCostPrice();
                } else if (item.getAlbum() != null) {
                    sum += item.getAlbum().getCostPrice();
                }
            }
        }
        return sum;
    }

    /**
     * Returns the total profit between the given date range.
     *
     * @param start start of the date range period.
     * @param end end of the date range period.
     * @return total profit.
     */
    public double getTotalProfit(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        return getSalesAmount(start, end) - getTotalCost(start, end);
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public long getTrackCount(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        Join items = root.join("orderItems");
        query.select(cb.count(items.get("track")));
        query.where(cb.between(root.<LocalDateTime>get("orderDate"), start, end));
        long l = entityManager.createQuery(query).getSingleResult();
        log.info(l + "");
        return l;
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public long getAlbumCount(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }

        if (start.isAfter(end)) {
            LocalDateTime temp = start;
            start = end;
            end = temp;
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        Join items = root.join("orderItems");
        query.select(cb.count(items.get("album")));
        query.where(cb.between(root.<LocalDateTime>get("orderDate"), start, end));
        long l = entityManager.createQuery(query).getSingleResult();
        log.info(l + "");
        return l;
    }
}
