package com.fractals.controllers;

import com.fractals.beans.Review;
import com.fractals.beans.Review_;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.User;
import com.fractals.beans.Track;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.UserTransaction;

/**
 *
 * @author Thai-Vu Nguyen
 * 
 */
@Named
@RequestScoped
public class ReviewJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em;
    
    public EntityManager getEntityManager(){ return em;}

    public void create(Review review) throws RollbackFailureException, Exception {
        
        try {
            utx.begin();
            User user = review.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                review.setUser(user);
            }
            Track track = review.getTrack();
            if (track != null) {
                track = em.getReference(track.getClass(), track.getId());
                review.setTrack(track);
            }
            em.persist(review);
            if (user != null) {
                user.getReviews().add(review);
                user = em.merge(user);
            }
            if (track != null) {
                track.getReviews().add(review);
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

    public void edit(Review review) throws NonexistentEntityException, RollbackFailureException, Exception {
       
        try {
            utx.begin();
            Review persistentReview = em.find(Review.class, review.getId());
            User userOld = persistentReview.getUser();
            User userNew = review.getUser();
            Track trackOld = persistentReview.getTrack();
            Track trackNew = review.getTrack();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                review.setUser(userNew);
            }
            if (trackNew != null) {
                trackNew = em.getReference(trackNew.getClass(), trackNew.getId());
                review.setTrack(trackNew);
            }
            review = em.merge(review);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getReviews().remove(review);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getReviews().add(review);
                userNew = em.merge(userNew);
            }
            if (trackOld != null && !trackOld.equals(trackNew)) {
                trackOld.getReviews().remove(review);
                trackOld = em.merge(trackOld);
            }
            if (trackNew != null && !trackNew.equals(trackOld)) {
                trackNew.getReviews().add(review);
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
                Integer id = review.getId();
                if (findReview(id) == null) {
                    throw new NonexistentEntityException("The review with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        
        try {
            utx.begin();

            Review review;
            try {
                review = em.getReference(Review.class, id);
                review.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The review with id " + id + " no longer exists.", enfe);
            }
            User user = review.getUser();
            if (user != null) {
                user.getReviews().remove(review);
                user = em.merge(user);
            }
            Track track = review.getTrack();
            if (track != null) {
                track.getReviews().remove(review);
                track = em.merge(track);
            }
            em.remove(review);
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

    public List<Review> findReviewEntities() {
        return findReviewEntities(true, -1, -1);
    }

    public List<Review> findReviewEntities(int maxResults, int firstResult) {
        return findReviewEntities(false, maxResults, firstResult);
    }

    private List<Review> findReviewEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Review.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public Review findReview(Integer id) {
        return em.find(Review.class, id);

    }

    public int getReviewCount() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Review> rt = cq.from(Review.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }
    
    
    /**
     * Module to get the number of reviews of a Track
     * @param track
     * @return Count of Reviews of in Track
     */
    public Number getReviewsCountPerTrack(Track track){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        
        Root<Review> root = query.from(Review.class);
        query.select(cb.count(root));
        query.where(cb.equal(root.get(Review_.track), track));
        
        return em.createQuery(query).getSingleResult();
    }
    
    /**
     * Module to get the approved comments of a Track
     * @param track
     * @return 
     * @author Thai-Vu Nguyen
     */
    public List<Review> getApprovedReviews(Track track){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.and(
                cb.equal(root.get(Review_.track), track),
                cb.equal(root.get(Review_.approved), true)
        ));
        
        return em.createQuery(query).getResultList();
        
    }

}
