package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.controllers.LoginController;
import com.fractals.controllers.ReviewsWebController;
import com.fractals.controllers.SimilarTracksController;
import com.fractals.controllers.TrackJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Backing bean for the Track client view
 *
 * @author Thai-Vu Nguyen, Danieil Skrinikov
 */
@Named("trackCLBack")
@RequestScoped
public class TrackClientBacking implements Serializable {

    private static final Logger log = Logger.getLogger("DatabaseSeedManager.class");

    private Integer trackId;
    private Track track;
    private Review review;
    

    @Inject
    private TrackJpaController trackControl;

    @Inject
    private ReviewsWebController reviewsControl;

    @Inject
    private SimilarTracksController similarControl;

    @Inject
    private ShoppingCart cart;
    
    @Inject
    private LoginController loginControl;

    /**
     * Initialize the Track entity based on the trackId
     */
    public void init() {
        track = trackControl.findTrack(trackId);
    }

    /**
     * Action to add a review to a track
     * @return 
     */
    public String addReview() {
        if (loginControl.isLoggedIn())
            this.review.setUser(loginControl.getCurrentUser());
        else{
            return null;
        }
        this.review.setApproved(false);
        this.reviewsControl.addReview(this.review);
        this.review.setTrack(track);
        
        return null;
    }

    /**
     * Adding the current instance of the Track to the shopping cart
     */
    public void addToCart() {
        this.cart.add(track);
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getArtists() {
        if (track == null) {
            return "";
        }
        List<Artist> artists = track.getArtists();
        if (artists == null || artists.isEmpty()) {
            return "";
        }

        String formattedArtists = "";
        for (int i = 0; i < artists.size(); i++) {
            formattedArtists += artists.get(i).getName();
            if (i < artists.size() - 1) {
                formattedArtists += ", ";
            }
        }
        return formattedArtists;

    }

    public Review getReview(){
        if (review == null)
            review = new Review();
        return review;
    }
    
    public List<Track> getSimilarTracks() {
        return similarControl.getSimilarTracks(track);
    }

    public List<Review> getReviews() {
        return (track != null) ? track.getReviews() : (new ArrayList<Review>());
    }

    
    public Integer getTrackId() {
        return this.trackId;
    }

    public Track getTrack() {
        return this.track;
    }

    public Album getAlbum() {
        return (track != null) ? track.getAlbum() : (new Album());
    }

    public String getTitle() {
        return (track != null) ? track.getTitle() : "";
    }

    public boolean isAvailable() {
        return (track != null) ? track.getAvailable() : false;
    }

    public boolean isIndividual() {
        return (track != null) ? track.getIsIndividual() : false;
    }
    
    public boolean reviewFormRender(){
        return loginControl.isLoggedIn();
    }

    public double getListedPrice() {
        return (track != null) ? track.getListPrice() : 0.00;
    }

    public double getSalesPrice() {
        return (track != null) ? track.getSalePrice() : 0.00;
    }

    public String getDuration() {
        return (track != null) ? track.getDuration() : "";
    }

    public String getWriter() {
        return (track != null) ? track.getSongwriter() : "";
    }

    /**
     * Returns the real price for the track. If there is a sale, returns the
     * sale price, if not returns the list price.
     *
     * @param track track to fetch the information from.
     * @return the display price of the track.
     */
    public double getPrice(Track track) {
        log.info("Track Backing - track sale check:" + track.getTitle());
        return (track.getSalePrice() == 0) ? track.getListPrice() : track.getSalePrice();
    }

}
