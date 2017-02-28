package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

    @Inject
    ReviewJpaController reviewControl;
    
    /**
     * Submit a Review
     * @param review
     * @return true if added, false if not added
     */
    public void addReview (Review review) throws Exception{
        
            reviewControl.create(review);
            
    }
    
    /**
     * Submits a review by a user for a track
     * @param track Track
     * @param message String
     * @param user User
     * @param rating int
     * @return true if added, false if not added
     */
    public boolean addReview(Track track, String message, User user, int rating){
        try {
            Review review = buildBean(track, message, user, rating);
            reviewControl.create(review);
            return true;
        } catch (Exception ex) {
            return false;
        }
        
    }
    
    /**
     * Fills the Review bean
     * @param track Track
     * @param message String
     * @param user User
     * @param rating int
     * @return Review
     */
    private Review buildBean(Track track, String message, User user, int rating){
        Review review = new Review();
        review.setRating(rating);
        review.setTrack(track);
        review.setUser(user);
        review.setText(message);
        review.setReviewDate(LocalDateTime.now());
        review.setApproved(false);
        return review;
    }
}
