package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.AlbumJpaController;
import com.fractals.controllers.ClientTrackingController;
import com.fractals.controllers.ReviewJpaController;
import com.fractals.controllers.ReviewsWebController;
import com.fractals.controllers.TrackJpaController;
import com.fractals.jsf.util.PaginationHelper;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Backing bean for the Track client view
 *
 * @author Thai-Vu Nguyen, Danieil Skrinikov
 */
@Named("trackCLBack")
@SessionScoped
public class TrackClientBacking implements Serializable {

    private static final Logger log = Logger.getLogger("TrackClientBacking.class");

    private List<Track> relatedTracks;
    private List<Album> relatedAlbums;
    private Integer trackId;
    private Track track;
    private Review review;
    private Integer rating;

    @Inject
    private TrackJpaController trackControl;

    @Inject
    private AlbumJpaController albumControl;

    @Inject
    private ReviewsWebController reviewsControl;

    @Inject
    private ClientTrackingController cookiesControl;

    @Inject
    private ShoppingCart cart;

    @Inject
    private LoginBacking loginControl;

    /**
     * Initialize the Track entity based on the trackId
     */
    public void init() {
        if (trackId == null) {

            log.info("Track is NULL");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String outcome = "/error.xhtml";
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
        } else {
            track = trackControl.findTrack(trackId);
            cookiesControl.saveGenre(track.getGenre());
        }

    }

    /**
     * Action to add a review to a track
     *
     * @return
     */
    public String addReview() throws Exception {

        if (loginControl.isLoggedIn()) {

            getReview().setUser(loginControl.getCurrentUser());
        } else {
            return "/index.xhtml";
        }
        getReview().setApproved(false);
        getReview().setTrack(track);
        getReview().setReviewDate(LocalDateTime.now());

        boolean created = this.reviewsControl.addReview(review);

        return "Track.xhtml?faces-redirect=true&id=" + trackId.intValue();

    }

    /**
     * Adding the current instance of the Track to the shopping cart
     */
    public String addToCart() {

        this.cart.add(track);
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
        return "shopping_cart.xhtml?faces-redirect=true&url=" + uri;
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

    public Review getReview() {
        if (review == null) {
            review = new Review();
        }
        return review;
    }

    /**
     * Module to return a list of similar Tracks to the current instance of the
     * track
     *
     * @return List of Track
     */
    public List<Track> getSimilarTracks() {

        if (track == null) {
            return new ArrayList<>();
        }
        return relatedTracks = trackControl.getSimilarTracks(track);
    }

    /**
     * Module to return a list of similar Albums to the current Track
     *
     * @return List of Album
     */
    public List<Album> getRelatedAlbums() {
        if (track == null) {
            return new ArrayList<>();
        }
        return relatedAlbums = albumControl.getSimilarAlbums(track.getAlbum(), 3);
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public boolean isLoggedIn() {
        return loginControl.isLoggedIn();
    }

}
