package com.fractals.controllers;

import com.fractals.backingbeans.ShoppingCart;
import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.email.EmailSender;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.RollbackException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * The controller for orders and purchasing logic.
 *
 * @author Aline Shulzhenko
 * @version 28/02/2017
 * @since 1.8
 */
@Named("ordersController")
@RequestScoped
public class OrderJPAController {
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;
    @EJB
    private OrderService orderBack;
    
    private boolean interrupted = false;
    
    /**
     * Submits and persists the order from the shopping cart.
     * Shopping cart is cleared if the persistence was successful.
     * The email is sent to the user, notifying about the recent purchase.
     * @param cart The shopping cart.
     * @param user The user that submits the order.
     * @return the order that was submitted.
     */
    public Order submitOrder(ShoppingCart cart, User user) {
        Order order = saveOrder(cart, user);   
        if(! interrupted) {           
            sendEmail(user, order);
            cart.empty();
            return order;
        }
        return null;
    }
    
    /**
     * Persists the order to the database.
     * @param cart The shopping cart with all items from the order.
     * @param user The user that submits the order.
     * @return the order that was persisted.
     */
    private Order saveOrder(ShoppingCart cart, User user) {
        Order order = new Order();
        order.setNetCost(calculateNetCost(cart));
        order.setUser(user);
        order.setGrossCost(0);
        order.setOrderDate(LocalDateTime.now());
        orderBack.saveOrder(order);
        
        List<Album> albums = cart.getAllAlbums();
        List<Track> tracks = cart.getAllTracks();
        
        saveAlbumItems(albums, order);
        saveTrackItems(tracks, order);
        
        order = orderBack.refreshOrder(order);
        return order;
    }
    
    /**
     * Persists tracks items from the shopping cart.
     * @param tracks Tracks to persist to the database.
     * @param order The order that contains these tracks.
     */
    private void saveTrackItems(List<Track> tracks, Order order) {
        try {
            userTransaction.begin();
            for(Track track : tracks) {
                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                double price = track.getSalePrice() != 0 ? track.getSalePrice() : track.getListPrice();
                oi.setCost(price);
                oi.setTrack(track);
                entityManager.persist(oi);        
            }          
            userTransaction.commit();
        } 
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | 
               HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                interrupted = true;
                userTransaction.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException re) {}
        }
    }
    
    /**
     * Persists albums items from the shopping cart.
     * @param albums Albums to persist to the database.
     * @param order The order that contains these albums.
     */
    private void saveAlbumItems(List<Album> albums, Order order) {
        try {
            userTransaction.begin();
            for(Album album : albums) {
                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                double price = album.getSalePrice() != 0 ? album.getSalePrice() : album.getListPrice();
                oi.setCost(price);
                oi.setAlbum(album);
                entityManager.persist(oi);        
            }          
            userTransaction.commit();
        } 
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | 
               HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                interrupted = true;
                userTransaction.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException re) {}
        }
    }
    
    /**
     * Sens the email to the user, notifying about the recent purchase.
     * @param u The current user.
     * @param order The newly created order for this user.
     */
    private void sendEmail(User u, Order order) {
        EmailSender es = new EmailSender(u);
        es.sendEmail(order);
    }
    
    /**
     * Calculates the net cost of the order based on the items in the shopping cart.
     * @param cart The cart for which items the net cost is calculated.
     * @return the net cost of the order.
     */
    private double calculateNetCost(ShoppingCart cart) {
        List<Object> items = cart.getAll();
        double cost = 0;
        for(Object o : items) {
            if(o instanceof Album) {
                Album album = (Album)o;
                cost += album.getSalePrice() != 0 ? album.getSalePrice() : album.getListPrice();
            }
            else if(o instanceof Track) {
                Track track = (Track)o;
                cost += track.getSalePrice() != 0 ? track.getSalePrice() : track.getListPrice();
            }
        }
        return cost;
    }
    
}
