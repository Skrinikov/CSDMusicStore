package com.fractals.controllers;

import com.fractals.beans.Review;
import com.fractals.beans.Review_;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named
@RequestScoped
public class ReviewController implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Review> getPendingReviews() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.isTrue(root.get(Review_.pending)));
        return em.createQuery(query).getResultList();
    }

    public List<Review> getApprovedReviews() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(
                cb.and(
                        cb.isTrue(root.get(Review_.approved)),
                        cb.isFalse(root.get(Review_.pending))
                )
        );
        return em.createQuery(query).getResultList();
    }

    public List<Review> getDisapprovedReviews() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(
                cb.and(
                        cb.isFalse(root.get(Review_.approved)),
                        cb.isFalse(root.get(Review_.pending))
                )
        );
        return em.createQuery(query).getResultList();
    }

    public List<Review> getReviewsByUser(User u) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.equal(root.get(Review_.user), u));
        return em.createQuery(query).getResultList();
    }

    public List<Review> getReviewsByTrack(Track t) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.equal(root.get(Review_.track), t));
        return em.createQuery(query).getResultList();
    }
}
