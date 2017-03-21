package com.fractals.backingbeans;

import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Sarah Mouffok, Danieil Skrinikov
 */
@Named("theReviews")
@SessionScoped
public class ReviewBackingBean implements Serializable {

    @Inject
    private ReviewJpaController reviewJpaController;

    private static final Logger log = Logger.getLogger("ReviewBackingBean.class");

    private Review selectedReview;

    public Review getSelectedReview() {
        return selectedReview;
    }

    public void setSelectedReview(Review r) {
        selectedReview = r;
    }

    private User selectedUser;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User u) {
        selectedUser = u;
    }

    private Track selectedTrack;

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(Track t) {
        selectedTrack = t;
    }

    public List<Review> getReviews() {
        return reviewJpaController.findReviewEntities();
    }

    public String disapproveReview() throws Exception {
        return editReview(false);
    }

    public String approveReview() throws Exception {
        return editReview(true);
    }

    public String editReview(boolean b) throws Exception {
        selectedReview.setApproved(b);
        selectedReview.setPending(false);
        reviewJpaController.edit(selectedReview);
        return "/management/review/reviewsView.xhtml";
    }

    public List<Review> getPendingReviews() {
        CriteriaBuilder cb = reviewJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        query.where(cb.isTrue(root.get("pending")));
        return reviewJpaController.getEntityManager().createQuery(query).getResultList();
    }

    public List<Review> getApprovedReviews() {
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

    public List<Review> getDisapprovedReviews() {
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
