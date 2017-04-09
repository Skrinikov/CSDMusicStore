package com.fractals.controllers;

import com.fractals.beans.Review;
import com.fractals.beans.Review_;
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
 * Controller for reviews
 * @author MOUFFOK Sarah
 */
@Named
@RequestScoped
public class ReviewController implements Serializable {

    @PersistenceContext
    private EntityManager em;

    /**
     * @return a list of all pending reviews
     */
    public List<Review> getPendingReviews() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.isTrue(root.get(Review_.pending)));
        return em.createQuery(query).getResultList();
    }

    /**
     * @return a list of all approved reviews
     */
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

    /**
     * @return a list of all disapproved reviews
     */
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

}
