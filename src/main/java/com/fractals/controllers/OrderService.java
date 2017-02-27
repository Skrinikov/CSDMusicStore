package com.fractals.controllers;

import com.fractals.beans.Order;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * A class responsible for managing orders database persistence and updates.
 *
 * @author Aline Shulzhenko
 * @version 26/02/2017
 * @since 1.8
 */
@Stateless
public class OrderService {
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
   
    
    /**
     * Saves this order to the database.
     * @param order The order to save to the database.
     */
    public void saveOrder(Order order) {
        entityManager.persist(order);
    }
    
    /**
     * Merges and refreshes the specified order.
     * @param order The order to merge and refresh.
     * @return the updated order value, synchronous with the database.
     */
    public Order refreshOrder(Order order) {
        Order o = entityManager.merge(order);
        entityManager.refresh(o);
        return o;
    }

}
