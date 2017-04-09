package com.fractals.backingbeans;

import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.ReviewController;
import com.fractals.controllers.ReviewJpaController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sarah Mouffok, Danieil Skrinikov
 */
@Named("theReviews")
@SessionScoped
public class ReviewBackingBean implements Serializable {

    @Inject
    private ReviewJpaController reviewJpaController;
    @Inject
    private ReviewController reviewController;
    
    private static final Logger log = Logger.getLogger("ReviewBackingBean.class");
    /**
     * For all the pages, this represents the selected review 
     * to view/approve/disapprove
     */
    private Review selectedReview;
     /**
     * For the reviewPerTrack Page, this is the track selected by the manager
     */
    private Track selectedTrack;
    /**
     * For the reviewPerUser Page, this is the user selected by the manager
     */
    private User selectedUser;  
    /**
     * For the reviewPerStatus Page, this String represents the status selected
     * by the manager
     */
    private String selection;

    public void setSelection(String s){selection = s;}
    public String getSelection(){return selection;}
    
    public Review getSelectedReview() {
        return selectedReview;
    }

    public void setSelectedReview(Review r) {
        selectedReview = r;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User u) {
        selectedUser = u;
        selectedReview = null;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(Track t) {
        selectedTrack = t;   
        selectedReview = null;
    }
    
    /**
     * @return a list of all existing reviews
     */
    public List<Review> getReviews() {
        return reviewJpaController.findReviewEntities();
    }
    
    /**
     * Disapproves the selectedReview
     * @throws Exception 
     */
    public void disapproveReview() throws Exception {
        editReview(false);
    }
    /**
     * Approves the selectedReview
     * @throws Exception 
     */
    public void approveReview() throws Exception {
        editReview(true);
    }

    /**
     * Method used to approve or disapprove a review
     * @param b, boolean that says if the review is being approved 
     * or disapproved
     * @throws Exception 
     */
    private void editReview(boolean b) throws Exception {
        selectedReview.setApproved(b);
        selectedReview.setPending(false);
        reviewJpaController.edit(selectedReview);
    }
    /**
     * Depending on the status (selection String) choice,
     * returns the appropriate list of reviews to display in a datatable
     * @return a list of Reviews
     */
    public List<Review> selectedReviews() {
        switch(selection){
            case "APPROVED":
                return reviewController.getApprovedReviews();
            case "DISAPPROVED":
                return reviewController.getDisapprovedReviews();
            case "PENDING":
                return reviewController.getPendingReviews();
            default: 
                return null;
        }
    }
    
    /**
     * @return all the Reviews written by the user selectedUser
     */
    public List<Review> getReviewsByUser() {
        return selectedUser.getReviews();
    }
    /**
     * @return all the Reviews for the track selectedTrack
     */
    public List<Review> getReviewsByTrack() {
        return selectedTrack.getReviews();
    }

       /**
     * Creates a graphic representation of the rating. Always returns 5 stars.
     * If the rating is 3 will return 3 full stars followed by two empty stars.
     *
     * @param r review for which to find a graphic representation of stars
     * @return html <span> tags.
     */
    public String getReviewStars(Review r) {
        String star = "<span class=fa fa-star></span>";
        String starEmpty = "<span class=fa fa-star></span>";
        String review = "";

        for (int i = 0; i < 5; i++) {
            if (r.getRating() - i <= 0) {
                review += starEmpty;
            } else {
                review += star;
            }
        }

        return review;
    }

    /**
     * Formats dates into a simpler user friendly display.
     *
     * @param local date to format
     * @return string representation of the formated date
     */
    public String formatDate(LocalDateTime local) {
        return local.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm a", Locale.CANADA));
    }

}
