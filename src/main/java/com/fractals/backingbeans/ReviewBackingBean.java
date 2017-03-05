/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.ReviewJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 1710030
 */

@Named("theReviews")
@SessionScoped
public class ReviewBackingBean implements Serializable {
    
    @Inject
    private ReviewJpaController reviewJpaController; 
    
    private Review selectedReview;
    public Review getSelectedReview(){ return selectedReview;}
    public void setSelectedReview(Review r){selectedReview = r;}
    
    
    private User selectedUser;
    public User getSelectedUser(){ return selectedUser;}
    public void setSelectedUser(User u){ selectedUser = u;}
    
    private Track selectedTrack;
    public Track getSelectedTrack(){ return selectedTrack;}
    public void setSelectedTrack(Track t){ selectedTrack = t;}
    
    public List<Review> getReviews(){return reviewJpaController.findReviewEntities();}
    public String disapproveReview() throws Exception {return editReview(false);}   
    public String approveReview()throws Exception {return editReview(true);} 
   
    public String editReview(boolean b) throws Exception {
        selectedReview.setApproved(b);
        selectedReview.setPending(false);
        reviewJpaController.edit(selectedReview);
        return "/management/review/reviewsView.xhtml"; 
    }   
     
    public List<Review> getPendingReviews(){
        CriteriaBuilder cb = reviewJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.isTrue(root.get("pending")));
        return reviewJpaController.getEntityManager().createQuery(query).getResultList();
    }
    
    public List<Review> getApprovedReviews(){
        CriteriaBuilder cb = reviewJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(
                cb.and(
                        cb.isTrue(root.get("approved")),
                        cb.isFalse(root.get("pending"))
                )
        );
        return reviewJpaController.getEntityManager().createQuery(query).getResultList();
    }
    
     public List<Review> getDisapprovedReviews(){
        CriteriaBuilder cb = reviewJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(
                cb.and(
                        cb.isFalse(root.get("approved")),
                        cb.isFalse(root.get("pending"))
                )
        );
        return reviewJpaController.getEntityManager().createQuery(query).getResultList();
    }
 
    public List<Review> getReviewsByUser() {
        CriteriaBuilder cb = reviewJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.equal(root.get("user"), selectedUser));
        return reviewJpaController.getEntityManager().createQuery(query).getResultList();
    }
    
    public List<Review> getReviewsByTrack() { 
        CriteriaBuilder cb = reviewJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.equal(root.get("track"), selectedTrack));
        return reviewJpaController.getEntityManager().createQuery(query).getResultList();
    }
    
    public String returnToPage() {
       selectedReview = null;
       selectedUser = null;
       selectedTrack = null;
       return "/management/review/pendingReviewsList.xhtml";    
    }
    
}
