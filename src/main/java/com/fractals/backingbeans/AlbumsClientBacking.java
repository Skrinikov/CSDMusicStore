package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.controllers.AlbumJpaController;
import com.fractals.controllers.ClientTrackingController;
import com.fractals.controllers.ReviewsWebController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * The Backing bean for the client side Album page
 *
 * @author Thai-Vu Nguyen, Danieil Skrinikov
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
    private ClientTrackingController cookiesControl;

    /*
    *   Initializes the Album entity
     */
    public void init() {
        album = albumControl.findAlbum(albumId);

        if (album == null) {
            log.info("Album is NULL");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String outcome = "/temp.xhtml"; // Maybe change to a Album 404 page.
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
        } else {

            similarAlbums = albumControl.getSimilarAlbums(album, 3);

            // Thai Vu I added this, because some albums have no tracks.
            if (album.getTracks().size() > 0) {
                cookiesControl.saveGenre(album.getTracks().get(0).getGenre());
            }
            isLoaded = true;
        }
    }

    /**
     * Function to add the current instance of the album to the shopping
     * @return Path to the shopping cart
     */
    public String addAlbumToCart() {
        shopControl.add(album);

        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI().toString();
        return "shopping_cart.xhtml?faces-redirect=true?url=" + uri;
    }

    /**
     * Function to add a selected Track to the Cart
     *
     * @return Path to the shopping cart
     */
    public String addTrackToCart() {
        shopControl.add(selectedTrack);

        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI().toString();
        return "shopping_cart.xhtml?faces-redirect=true?url=" + uri;

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
     */
    public double getPrice(Album album) {
        return (album.getSalePrice() <= 0) ? album.getListPrice() : album.getSalePrice();
    }

    /**
     * returns the cover image name of the album.
     *
     * @return
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
            return "/index.xhtml";
        }
        review.setApproved(false);
        review.setReviewDate(LocalDateTime.now());

        reviewControl.addReview(review);

        return "Album.xhtml?faces-redirect=true&id=" + albumId.intValue();

    }

    public void showReviewDialog() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("includeViewParams", true);

        //TODO
        //TESTING if the dialog pops out
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hello - ", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Check if the user is logged in
     *
     * @return true if logged in, false is not logged in
     */
    public boolean isLoggedIn() {
        return loginControl.isLoggedIn();
    }

}
