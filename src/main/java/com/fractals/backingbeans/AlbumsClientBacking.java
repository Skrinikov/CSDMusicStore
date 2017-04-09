package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.AlbumJpaController;
import com.fractals.controllers.ClientTrackingController;
import com.fractals.controllers.ReviewsWebController;
import com.fractals.controllers.TrackJpaController;
import com.fractals.controllers.UserJpaController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Backing bean for the client side Album page
 *
 * @author Danieil Skrinikov
 * @author Thai-Vu Nguyen
 */
@Named("albumsCLBack")
@SessionScoped
public class AlbumsClientBacking implements Serializable {

    private Album album;
    private Integer albumId;
    private List<Album> similarAlbums;
    private boolean isLoaded = false;
    private Track selectedTrack;
    private Review createdReview;
    private List<Review> reviews;

    private static final Logger log = Logger.getLogger("AlbumsClientBacking.class");

    @Inject
    AlbumJpaController albumControl;

    @Inject
    ShoppingCart shopControl;

    @Inject
    ReviewsWebController reviewControl;

    @Inject
    private LoginBacking loginControl;

    @Inject
    private TrackJpaController trackController;

    @Inject
    private ClientTrackingController cookiesControl;
    
    @Inject
    private UserJpaController userController;
    
    @Inject
    private TrackClientBacking trackBacking;

    /**
     * Initializes the Album entity
     * @author Danieil Skrinikov
     * @author Thai Vu Nguyen
     */
    public void init() {
        reviews = new ArrayList<>();
        
        if (albumId == null || albumControl.getAlbumCount()-1 < albumId) {
            log.info("Album is NULL or id is invalid: " + albumId);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String outcome = "/error.xhtml";
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
        } else {

            album = albumControl.findAlbum(albumId);
            similarAlbums = albumControl.getSimilarAlbums(album, 3);

            // Thai Vu I added this, because some albums have no tracks.
            if (album.getTracks().size() > 0) {
                cookiesControl.saveGenre(album.getTracks().get(0).getGenre());
            }
            isLoaded = true;
        }
    }

    public List<Track> getTracks() {
        return getAlbum().getTracks();
    }

    public Album getAlbum() {
        if (album == null) {
            if (albumId != null) {
                albumControl.findAlbum(albumId);
            } else {
                album = new Album();
            }
        }
        
        return album;
    }

    public Integer getAlbumId() {
        return this.albumId;
    }

    public Review getReview() {
        if (createdReview == null) {
            createdReview = new Review();
        }
        return createdReview;
    }

    public Track getSelectedTrack() {
        if (selectedTrack == null) {
            selectedTrack = new Track();
        }
        return selectedTrack;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public List<Album> getSimilarAlbums() {
        return similarAlbums;
    }

    public void setSimilarAlbums(List<Album> similarAlbums) {
        this.similarAlbums = similarAlbums;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public boolean isIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void setSelectedTrack(Track selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    /**
     * Returns the real price for the album. If there is a sale, returns the
     * sale price, if not returns the list price.
     *
     * @param album Album to fetch the information from.
     * @return Real price for the album.
     * @author Danieil Skrinikov
     */
    public double getPrice(Album album) {
        return (album.getSalePrice() <= 0) ? album.getListPrice() : album.getSalePrice();
    }

    /**
     * returns the cover image name of the album.
     *
     * @return
     * @author Danieil Skrinikov
     */
    public String getAlbumCover() {
        if (!album.getTracks().isEmpty()) {
            return album.getTracks().get(0).getCoverFile();
        } else {
            return "2001.jpg";
        }
    }

    public String getAlbumCover(Album album) {
        return album.getTracks().get(0).getCoverFile();
    }

    public String addReview() {

        Review review = getReview();

        if (loginControl.isLoggedIn()) {
            review.setUser(loginControl.getCurrentUser());
        } else {
            return "/client/index.xhtml";
        }
        review.setApproved(false);
        review.setReviewDate(LocalDateTime.now());
        log.info("Text:" + review.getText());

        reviewControl.addReview(review);
        

        return "Album.xhtml?faces-redirect=true&id=" + albumId.intValue();

    }

    /**
     * Adds a review to the database and rereshes the page
     * 
     * @param trackId
     * @param reviewText
     * @param rating
     * @return location where to go after this action is completed.
     * @author Danieil Skriniov
     */
    public String addReview(int trackId, String reviewText, int rating) {
        Track track = trackController.findTrack(trackId);
        User user = loginControl.getCurrentUser();

        if (track == null) {
            return "/client/index.xhtml";
        }
        if (user == null) {
            return "/client/login.xhtml";
        }
        Review review = new Review();
        review.setTrack(track);
        review.setText(reviewText);
        review.setApproved(false);
        review.setPending(true);
        review.setUser(user);
        review.setRating(rating);
        review.setReviewDate(LocalDateTime.now());       
        reviewControl.addReview(review);
        trackBacking.addReviewed(track);
        
        // Refresh the page
        return null;
    }

}
