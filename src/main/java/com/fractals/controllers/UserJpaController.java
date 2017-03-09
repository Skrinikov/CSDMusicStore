/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.backingbeans.exceptions.IllegalOrphanException;
import com.fractals.backingbeans.exceptions.NonexistentEntityException;
import com.fractals.backingbeans.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.Province;
import com.fractals.beans.Genre;
import com.fractals.beans.Review;
import java.util.ArrayList;
import java.util.List;
import com.fractals.beans.Order;
import com.fractals.beans.User;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1710030
 */

@Named
@SessionScoped
public class UserJpaController implements Serializable {
   
    @Resource
    private UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager(){ return em;}
    
    public void create(User user) throws RollbackFailureException, Exception {
        if (user.getReviews() == null) {
            user.setReviews(new ArrayList<Review>());
        }
        if (user.getOrders() == null) {
            user.setOrders(new ArrayList<Order>());
        }

        try {
            utx.begin();
            Province province = user.getProvince();
            if (province != null) {
                province = em.getReference(province.getClass(), province.getId());
                user.setProvince(province);
            }
            Genre lastGenre = user.getLastGenre();
            if (lastGenre != null) {
                lastGenre = em.getReference(lastGenre.getClass(), lastGenre.getId());
                user.setLastGenre(lastGenre);
            }
            List<Review> attachedReviews = new ArrayList<Review>();
            for (Review reviewsReviewToAttach : user.getReviews()) {
                reviewsReviewToAttach = em.getReference(reviewsReviewToAttach.getClass(), reviewsReviewToAttach.getId());
                attachedReviews.add(reviewsReviewToAttach);
            }
            user.setReviews(attachedReviews);
            List<Order> attachedOrders = new ArrayList<Order>();
            for (Order ordersOrderToAttach : user.getOrders()) {
                ordersOrderToAttach = em.getReference(ordersOrderToAttach.getClass(), ordersOrderToAttach.getId());
                attachedOrders.add(ordersOrderToAttach);
            }
            user.setOrders(attachedOrders);
            em.persist(user);
            if (province != null) {
                province.getUsers().add(user);
                province = em.merge(province);
            }
            if (lastGenre != null) {
                lastGenre.getUsers().add(user);
                lastGenre = em.merge(lastGenre);
            }
            for (Review reviewsReview : user.getReviews()) {
                User oldUserOfReviewsReview = reviewsReview.getUser();
                reviewsReview.setUser(user);
                reviewsReview = em.merge(reviewsReview);
                if (oldUserOfReviewsReview != null) {
                    oldUserOfReviewsReview.getReviews().remove(reviewsReview);
                    oldUserOfReviewsReview = em.merge(oldUserOfReviewsReview);
                }
            }
            for (Order ordersOrder : user.getOrders()) {
                User oldUserOfOrdersOrder = ordersOrder.getUser();
                ordersOrder.setUser(user);
                ordersOrder = em.merge(ordersOrder);
                if (oldUserOfOrdersOrder != null) {
                    oldUserOfOrdersOrder.getOrders().remove(ordersOrder);
                    oldUserOfOrdersOrder = em.merge(oldUserOfOrdersOrder);
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

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            User persistentUser = em.find(User.class, user.getId());
            Province provinceOld = persistentUser.getProvince();
            Province provinceNew = user.getProvince();
            Genre lastGenreOld = persistentUser.getLastGenre();
            Genre lastGenreNew = user.getLastGenre();
            List<Review> reviewsOld = persistentUser.getReviews();
            List<Review> reviewsNew = user.getReviews();
            List<Order> ordersOld = persistentUser.getOrders();
            List<Order> ordersNew = user.getOrders();
            List<String> illegalOrphanMessages = null;
            for (Review reviewsOldReview : reviewsOld) {
                if (!reviewsNew.contains(reviewsOldReview)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Review " + reviewsOldReview + " since its user field is not nullable.");
                }
            }
            for (Order ordersOldOrder : ordersOld) {
                if (!ordersNew.contains(ordersOldOrder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Order " + ordersOldOrder + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (provinceNew != null) {
                provinceNew = em.getReference(provinceNew.getClass(), provinceNew.getId());
                user.setProvince(provinceNew);
            }
            if (lastGenreNew != null) {
                lastGenreNew = em.getReference(lastGenreNew.getClass(), lastGenreNew.getId());
                user.setLastGenre(lastGenreNew);
            }
            List<Review> attachedReviewsNew = new ArrayList<Review>();
            for (Review reviewsNewReviewToAttach : reviewsNew) {
                reviewsNewReviewToAttach = em.getReference(reviewsNewReviewToAttach.getClass(), reviewsNewReviewToAttach.getId());
                attachedReviewsNew.add(reviewsNewReviewToAttach);
            }
            reviewsNew = attachedReviewsNew;
            user.setReviews(reviewsNew);
            List<Order> attachedOrdersNew = new ArrayList<Order>();
            for (Order ordersNewOrderToAttach : ordersNew) {
                ordersNewOrderToAttach = em.getReference(ordersNewOrderToAttach.getClass(), ordersNewOrderToAttach.getId());
                attachedOrdersNew.add(ordersNewOrderToAttach);
            }
            ordersNew = attachedOrdersNew;
            user.setOrders(ordersNew);
            user = em.merge(user);
            if (provinceOld != null && !provinceOld.equals(provinceNew)) {
                provinceOld.getUsers().remove(user);
                provinceOld = em.merge(provinceOld);
            }
            if (provinceNew != null && !provinceNew.equals(provinceOld)) {
                provinceNew.getUsers().add(user);
                provinceNew = em.merge(provinceNew);
            }
            if (lastGenreOld != null && !lastGenreOld.equals(lastGenreNew)) {
                lastGenreOld.getUsers().remove(user);
                lastGenreOld = em.merge(lastGenreOld);
            }
            if (lastGenreNew != null && !lastGenreNew.equals(lastGenreOld)) {
                lastGenreNew.getUsers().add(user);
                lastGenreNew = em.merge(lastGenreNew);
            }
            for (Review reviewsNewReview : reviewsNew) {
                if (!reviewsOld.contains(reviewsNewReview)) {
                    User oldUserOfReviewsNewReview = reviewsNewReview.getUser();
                    reviewsNewReview.setUser(user);
                    reviewsNewReview = em.merge(reviewsNewReview);
                    if (oldUserOfReviewsNewReview != null && !oldUserOfReviewsNewReview.equals(user)) {
                        oldUserOfReviewsNewReview.getReviews().remove(reviewsNewReview);
                        oldUserOfReviewsNewReview = em.merge(oldUserOfReviewsNewReview);
                    }
                }
            }
            for (Order ordersNewOrder : ordersNew) {
                if (!ordersOld.contains(ordersNewOrder)) {
                    User oldUserOfOrdersNewOrder = ordersNewOrder.getUser();
                    ordersNewOrder.setUser(user);
                    ordersNewOrder = em.merge(ordersNewOrder);
                    if (oldUserOfOrdersNewOrder != null && !oldUserOfOrdersNewOrder.equals(user)) {
                        oldUserOfOrdersNewOrder.getOrders().remove(ordersNewOrder);
                        oldUserOfOrdersNewOrder = em.merge(oldUserOfOrdersNewOrder);
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
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Review> reviewsOrphanCheck = user.getReviews();
            for (Review reviewsOrphanCheckReview : reviewsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Review " + reviewsOrphanCheckReview + " in its reviews field has a non-nullable user field.");
            }
            List<Order> ordersOrphanCheck = user.getOrders();
            for (Order ordersOrphanCheckOrder : ordersOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Order " + ordersOrphanCheckOrder + " in its orders field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Province province = user.getProvince();
            if (province != null) {
                province.getUsers().remove(user);
                province = em.merge(province);
            }
            Genre lastGenre = user.getLastGenre();
            if (lastGenre != null) {
                lastGenre.getUsers().remove(user);
                lastGenre = em.merge(lastGenre);
            }
            em.remove(user);
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

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public User findUser(Integer id) {
        return em.find(User.class, id);
    }

    public int getUserCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<User> rt = cq.from(User.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public boolean isEmpty(){
        return getUserCount() == 0;
    }
    
    /**
     * Searches users table by the username.
     * @param username The username to search by in the users table.
     * @return the user with the corresponding username.
     */
    public User getByUsername(String username) {
        return (User)em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
    }
}
