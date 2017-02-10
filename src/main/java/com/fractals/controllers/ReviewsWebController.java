package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Thai-Vu Nguyen
 */
@Named("reviewsController")
@RequestScoped
public class ReviewsWebController implements Serializable {
    
    public ReviewsWebController(){}
    
    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    /**
     * Submits a review by a user for a track
     * @param track Track
     * @param message String
     * @param user User
     * @param rating int
     * @return Review
     */
    public Review addReview(Track track, String message, User user, int rating){
        Review review = createReview(message, user, rating);
        updateReviewsInTrack(track, review);
        return review;
    }
    
    /**
     * Creates an instance of Review in the database
     * @param message String
     * @param user User
     * @param rating int
     * @return a new Review instance created in the database
     */
    private Review createReview (String message, User user, int rating){
        Review review = new Review();
        review.setUserId(user);
        review.setText(message);
        review.setApproved(false);
        review.setRating(rating);
        review.setReviewDate(Date.valueOf(LocalDate.now()));
        
        try{
            userTransaction.begin();
            
            entityManager.persist(review);
            
            userTransaction.commit();
            review = entityManager.find(Review.class, review.getId());
            entityManager.refresh(review);
        }catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | 
               HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                
                userTransaction.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException re) {}
            return null;
        }
        
        return review;
    }

    /**
     * Updates the list of reviews in a track
     * 
     * @param track Track
     * @param review Review
     */
    private void updateReviewsInTrack(Track track, Review review) {
        try {
            
            userTransaction.begin();
            Collection<Review> reviews = track.getReviewsCollection();
            reviews.add(review);
            track.setReviewsCollection(reviews);
            entityManager.persist(track);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | 
               HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                userTransaction.rollback();
            } 
            catch (IllegalStateException | SecurityException | SystemException re) {}            
        }
    }    
}
