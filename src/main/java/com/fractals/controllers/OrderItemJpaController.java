/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.Order;
import com.fractals.beans.Album;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
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
@Named("orderItemJpaController")
@RequestScoped
public class OrderItemJpaController implements Serializable {
    
    @Resource
    private UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(OrderItem orderItem) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            Order order = orderItem.getOrder();
            if (order != null) {
                order = em.getReference(order.getClass(), order.getId());
                orderItem.setOrder(order);
            }
            Album album = orderItem.getAlbum();
            if (album != null) {
                album = em.getReference(album.getClass(), album.getId());
                orderItem.setAlbum(album);
            }
            Track track = orderItem.getTrack();
            if (track != null) {
                track = em.getReference(track.getClass(), track.getId());
                orderItem.setTrack(track);
            }
            em.persist(orderItem);
            if (order != null) {
                order.getOrderItems().add(orderItem);
                order = em.merge(order);
            }
            if (album != null) {
                album.getOrderItems().add(orderItem);
                album = em.merge(album);
            }
            if (track != null) {
                track.getOrderItems().add(orderItem);
                track = em.merge(track);
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

    public void edit(OrderItem orderItem) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            OrderItem persistentOrderItem = em.find(OrderItem.class, orderItem.getId());
            Order orderOld = persistentOrderItem.getOrder();
            Order orderNew = orderItem.getOrder();
            Album albumOld = persistentOrderItem.getAlbum();
            Album albumNew = orderItem.getAlbum();
            Track trackOld = persistentOrderItem.getTrack();
            Track trackNew = orderItem.getTrack();
            if (orderNew != null) {
                orderNew = em.getReference(orderNew.getClass(), orderNew.getId());
                orderItem.setOrder(orderNew);
            }
            if (albumNew != null) {
                albumNew = em.getReference(albumNew.getClass(), albumNew.getId());
                orderItem.setAlbum(albumNew);
            }
            if (trackNew != null) {
                trackNew = em.getReference(trackNew.getClass(), trackNew.getId());
                orderItem.setTrack(trackNew);
            }
            orderItem = em.merge(orderItem);
            if (orderOld != null && !orderOld.equals(orderNew)) {
                orderOld.getOrderItems().remove(orderItem);
                orderOld = em.merge(orderOld);
            }
            if (orderNew != null && !orderNew.equals(orderOld)) {
                orderNew.getOrderItems().add(orderItem);
                orderNew = em.merge(orderNew);
            }
            if (albumOld != null && !albumOld.equals(albumNew)) {
                albumOld.getOrderItems().remove(orderItem);
                albumOld = em.merge(albumOld);
            }
            if (albumNew != null && !albumNew.equals(albumOld)) {
                albumNew.getOrderItems().add(orderItem);
                albumNew = em.merge(albumNew);
            }
            if (trackOld != null && !trackOld.equals(trackNew)) {
                trackOld.getOrderItems().remove(orderItem);
                trackOld = em.merge(trackOld);
            }
            if (trackNew != null && !trackNew.equals(trackOld)) {
                trackNew.getOrderItems().add(orderItem);
                trackNew = em.merge(trackNew);
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
                Integer id = orderItem.getId();
                if (findOrderItem(id) == null) {
                    throw new NonexistentEntityException("The orderItem with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            OrderItem orderItem;
            try {
                orderItem = em.getReference(OrderItem.class, id);
                orderItem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderItem with id " + id + " no longer exists.", enfe);
            }
            Order order = orderItem.getOrder();
            if (order != null) {
                order.getOrderItems().remove(orderItem);
                order = em.merge(order);
            }
            Album album = orderItem.getAlbum();
            if (album != null) {
                album.getOrderItems().remove(orderItem);
                album = em.merge(album);
            }
            Track track = orderItem.getTrack();
            if (track != null) {
                track.getOrderItems().remove(orderItem);
                track = em.merge(track);
            }
            em.remove(orderItem);
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

    public List<OrderItem> findOrderItemEntities() {
        return findOrderItemEntities(true, -1, -1);
    }

    public List<OrderItem> findOrderItemEntities(int maxResults, int firstResult) {
        return findOrderItemEntities(false, maxResults, firstResult);
    }

    private List<OrderItem> findOrderItemEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(OrderItem.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public OrderItem findOrderItem(Integer id) {
        return em.find(OrderItem.class, id);
    }

    public int getOrderItemCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<OrderItem> rt = cq.from(OrderItem.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
