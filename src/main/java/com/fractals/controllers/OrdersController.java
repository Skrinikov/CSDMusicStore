package com.fractals.controllers;

import com.fractals.backingbeans.ShoppingCart;
import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.email.EmailSender;
import java.util.List;
import javax.annotation.Resource;
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
 * @author Alena Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
@Named
@RequestScoped
public class OrdersController {
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;
    
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
            sendEmail(user, cart);
            cart.empty();
        }
        return order;
    }
    
    /**
     * Persists the order to the database.
     * @param cart The shopping cart with all items from the order.
     * @param user The user that submits the order.
     * @return the order that was persisted.
     */
    private Order saveOrder(ShoppingCart cart, User user) {
        Order order = new Order();
        order.setNetCost(0);
        order.setUser(user);
        entityManager.persist(order);
        
        List<Album> albums = cart.getAllAlbums();
        List<Track> tracks = cart.getAllTracks();
        
        double netCost = 0;
        netCost = saveAlbumItems(albums, order);
        netCost = saveTrackItems(tracks, order) + netCost;
        
        order.setNetCost(netCost);
        entityManager.merge(order);
        return order;
    }
    
    /**
     * Persists tracks items from the shopping cart and returns the net cost of all tracks.
     * @param tracks Tracks to persist to the database.
     * @param order The order that contains these tracks.
     * @return the net cost of all tracks.
     */
    private double saveTrackItems(List<Track> tracks, Order order) {
        double netCost = 0;
        try {
            userTransaction.begin();
            for(Track track : tracks) {
                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                double price = track.getSalePrice() != 0 ? track.getSalePrice() : track.getListPrice();
                oi.setCost(price);
                oi.setTrack(track);
                netCost += price;
                entityManager.persist(oi);        
            }          
            userTransaction.commit();
            return netCost;
        } 
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | 
               HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                interrupted = true;
                userTransaction.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException re) {}
            return 0;
        }
    }
    
    /**
     * Persists albums items from the shopping cart and returns the net cost of all albums.
     * @param albums Albums to persist to the database.
     * @param order The order that contains these albums.
     * @return the net cost of all tracks.
     */
    private double saveAlbumItems(List<Album> albums, Order order) {
        double netCost = 0;
        try {
            userTransaction.begin();
            for(Album album : albums) {
                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                double price = album.getSalePrice() != 0 ? album.getSalePrice() : album.getListPrice();
                oi.setCost(price);
                oi.setAlbum(album);
                netCost += price;
                entityManager.persist(oi);        
            }          
            userTransaction.commit();
            return netCost;
        } 
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | 
               HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                interrupted = true;
                userTransaction.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException re) {}
            return 0;
        }
    }
    
    /**
     * Sens the email to the user, notifying about the recent purchase.
     * @param u The current user.
     * @param cart All items bought by the user in the shopping cart.
     */
    private void sendEmail(User u, ShoppingCart cart) {
        EmailSender es = new EmailSender(u);
        es.sendEmail(cart);
    }
    
}
