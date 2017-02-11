package com.fractals.controllers;

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
 * i did it
 * @author lynn
 */
@Named
@RequestScoped
public class OrdersController {
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;
    
    private boolean interrupted = false;
    
    public Order submitOrder(ShoppingCart cart, User user) {
        Order order = saveOrder(cart, user);   
        if(! interrupted) {
            sendEmail(user, cart);
            cart.empty();
        }
        return order;
    }
    
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
    
    private void sendEmail(User u, ShoppingCart cart) {
        EmailSender es = new EmailSender(u);
        es.sendEmail(cart);
    }
    
    /*private Order createOrder(Order order) {
        try {
            userTransaction.begin();
            entityManager.persist(order);           
            userTransaction.commit();
            order = entityManager.find(Order.class, order.getId());
            entityManager.refresh(order);
            return order;
        } 
        catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | 
               HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                interrupted = true;
                userTransaction.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException re) {}
            return null;
        }
    }*/
    
}
