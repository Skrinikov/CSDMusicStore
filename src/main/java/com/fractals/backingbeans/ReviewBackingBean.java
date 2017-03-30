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
    private Review selectedReview;
    private Track selectedTrack;
    private User selectedUser;  
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

    public List<Review> getReviews() {
        return reviewJpaController.findReviewEntities();
    }

    public void disapproveReview() throws Exception {
        editReview(false);
    }

    public void approveReview() throws Exception {
        editReview(true);
    }

    private void editReview(boolean b) throws Exception {
        selectedReview.setApproved(b);
        selectedReview.setPending(false);
        reviewJpaController.edit(selectedReview);
    }
    
    public List<Review> selectedReviews() {
        if (selection.equals("APPROVED")) 
            return reviewController.getApprovedReviews();
        if (selection.equals("PENDING")) 
            return reviewController.getPendingReviews();
        if (selection.equals("DISAPPROVED"))
            return reviewController.getDisapprovedReviews();
        return null;
    }
    
    public List<Review> getReviewsByUser() {
        return reviewController.getReviewsByUser(selectedUser);
    }

    public List<Review> getReviewsByTrack() {
        return reviewController.getReviewsByTrack(selectedTrack);
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
