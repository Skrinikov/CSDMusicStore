package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.email.EmailSender;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
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
        Object[] items = cart.getAllItems();
        double netCost = 0;
        for(Object o : items) {
            if(o instanceof Album)
                netCost += ((Album)o).getListPrice();      
            else if(o instanceof Track) 
                netCost += ((Track)o).getListPrice();              
        }
        
        Order order = new Order();
        order.setNetCost(netCost);
        order.setUser(user);
        order = createOrder(order);
        saveOrderItems(order, items);
        return order;
    }
    
    private void saveOrderItems(Order order, Object[] items) {
        try {
            userTransaction.begin();
            for(Object o : items) {
                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                if(o instanceof Album) {
                    Album album = (Album)o;
                    double price = album.getSalePrice();
                    if(price != 0)
                        oi.setCost(price);
                    else
                        oi.setCost(album.getListPrice());
                    oi.setAlbum(album);
                }
                else if(o instanceof Track) {
                    Track track = (Track)o;
                    double price = track.getSalePrice();
                    if(price != 0)
                        oi.setCost(price);
                    else
                        oi.setCost(track.getListPrice());
                    oi.setTrack(track);
                }
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
    
    private Order createOrder(Order order) {
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
    }
    
    private void sendEmail(User u, ShoppingCart cart) {
        EmailSender es = new EmailSender(u);
        es.sendEmail(cart.getAllItems());
    }
}
