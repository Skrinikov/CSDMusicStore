/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.beans.Order;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.User;
import com.fractals.beans.OrderItem;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Sarah
 */
@Named("orderJpaController")
@RequestScoped
public class OrderJpaController implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(Order order) throws RollbackFailureException, Exception {
        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<OrderItem>());
        }
        try {
            utx.begin();
            User user = order.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                order.setUser(user);
            }
            List<OrderItem> attachedOrderItems = new ArrayList<OrderItem>();
            for (OrderItem orderItemsOrderItemToAttach : order.getOrderItems()) {
                orderItemsOrderItemToAttach = em.getReference(orderItemsOrderItemToAttach.getClass(), orderItemsOrderItemToAttach.getId());
                attachedOrderItems.add(orderItemsOrderItemToAttach);
            }
            order.setOrderItems(attachedOrderItems);
            em.persist(order);
            if (user != null) {
                user.getOrders().add(order);
                user = em.merge(user);
            }
            for (OrderItem orderItemsOrderItem : order.getOrderItems()) {
                Order oldOrderOfOrderItemsOrderItem = orderItemsOrderItem.getOrder();
                orderItemsOrderItem.setOrder(order);
                orderItemsOrderItem = em.merge(orderItemsOrderItem);
                if (oldOrderOfOrderItemsOrderItem != null) {
                    oldOrderOfOrderItemsOrderItem.getOrderItems().remove(orderItemsOrderItem);
                    oldOrderOfOrderItemsOrderItem = em.merge(oldOrderOfOrderItemsOrderItem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public void edit(Order order) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Order persistentOrder = em.find(Order.class, order.getId());
            User userOld = persistentOrder.getUser();
            User userNew = order.getUser();
            List<OrderItem> orderItemsOld = persistentOrder.getOrderItems();
            List<OrderItem> orderItemsNew = order.getOrderItems();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                order.setUser(userNew);
            }
            List<OrderItem> attachedOrderItemsNew = new ArrayList<OrderItem>();
            for (OrderItem orderItemsNewOrderItemToAttach : orderItemsNew) {
                orderItemsNewOrderItemToAttach = em.getReference(orderItemsNewOrderItemToAttach.getClass(), orderItemsNewOrderItemToAttach.getId());
                attachedOrderItemsNew.add(orderItemsNewOrderItemToAttach);
            }
            orderItemsNew = attachedOrderItemsNew;
            order.setOrderItems(orderItemsNew);
            order = em.merge(order);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getOrders().remove(order);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getOrders().add(order);
                userNew = em.merge(userNew);
            }
            for (OrderItem orderItemsOldOrderItem : orderItemsOld) {
                if (!orderItemsNew.contains(orderItemsOldOrderItem)) {
                    orderItemsOldOrderItem.setOrder(null);
                    orderItemsOldOrderItem = em.merge(orderItemsOldOrderItem);
                }
            }
            for (OrderItem orderItemsNewOrderItem : orderItemsNew) {
                if (!orderItemsOld.contains(orderItemsNewOrderItem)) {
                    Order oldOrderOfOrderItemsNewOrderItem = orderItemsNewOrderItem.getOrder();
                    orderItemsNewOrderItem.setOrder(order);
                    orderItemsNewOrderItem = em.merge(orderItemsNewOrderItem);
                    if (oldOrderOfOrderItemsNewOrderItem != null && !oldOrderOfOrderItemsNewOrderItem.equals(order)) {
                        oldOrderOfOrderItemsNewOrderItem.getOrderItems().remove(orderItemsNewOrderItem);
                        oldOrderOfOrderItemsNewOrderItem = em.merge(oldOrderOfOrderItemsNewOrderItem);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = order.getId();
                if (findOrder(id) == null) {
                    throw new NonexistentEntityException("The order with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Order order;
            try {
                order = em.getReference(Order.class, id);
                order.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The order with id " + id + " no longer exists.", enfe);
            }
            User user = order.getUser();
            if (user != null) {
                user.getOrders().remove(order);
                user = em.merge(user);
            }
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItemsOrderItem : orderItems) {
                orderItemsOrderItem.setOrder(null);
                orderItemsOrderItem = em.merge(orderItemsOrderItem);
            }
            em.remove(order);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Order> findOrderEntities() {
        return findOrderEntities(true, -1, -1);
    }

    public List<Order> findOrderEntities(int maxResults, int firstResult) {
        return findOrderEntities(false, maxResults, firstResult);
    }

    private List<Order> findOrderEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Order.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public Order findOrder(Integer id) {
        return em.find(Order.class, id);

    }

    public int getOrderCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Order> rt = cq.from(Order.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}


