package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.controllers.ClientTrackingController;
import com.fractals.controllers.LoginController;
import com.fractals.controllers.ReviewJpaController;
import com.fractals.controllers.ReviewsWebController;
import com.fractals.controllers.SimilarTracksController;
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
@RequestScoped
public class TrackClientBacking implements Serializable {

    private static final Logger log = Logger.getLogger("TrackClientBacking.class");
    
    // Strings used for cost buttons;
    private static final String available = "/WEB-INF/sections/client/trackTableRowAvailable.xhtml";
    private static final String unavailable = "/WEB-INF/sections/client/trackTableRowUnavailable.xhtml";
    private static final String inCart = "/WEB-INF/sections/client/trackTableRowInCart.xhtml";

    private List<Track> relatedTracks;
    private Integer trackId;
    private Track track;    
    private Review review;
    private Integer rating;
    private User user;
    
    private PaginationHelper pagination;
    private int reviewItemIndex;
    private DataModel datamodel;

    @Inject
    private TrackJpaController trackControl;

    @Inject
    private ReviewsWebController reviewsControl;

    @Inject
    private ClientTrackingController cookiesControl;
    
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
        cookiesControl.saveGenre(track.getGenre());
    }

    /**
     * Action to add a review to a track
     * @return 
     */
    public String addReview() throws Exception{
        
        track = trackControl.findTrack(trackId);
        if (loginControl.isLoggedIn()){
            
            getReview().setUser(loginControl.getCurrentUser());
        }
        else{
            return "/index.xhtml";
        }
        getReview().setApproved(false);
        getReview().setTrack(track);
        getReview().setReviewDate(LocalDateTime.now());
        
        boolean created = this.reviewsControl.addReview(review);
        //review2Control.create(review);
        
        return "Track.xhtml?faces-redirect=true&id=" + trackId.intValue();
        
    }
    
    public String testRedirect(){
        return "client/Track.xhtml?id=3";
    }

    /**
     * Adding the current instance of the Track to the shopping cart
     */
    public String addToCart() {
        track = trackControl.findTrack(trackId);
        this.cart.add(track);
        
        return "Track.xhtml?faces-redirect=true&id=" + trackId.toString();
    }
    
    ////////////Pagination Logic For Reviews///////////////
    
    public PaginationHelper getPagination(){
        if (pagination == null){
            pagination = new PaginationHelper(10){
                @Override
                public int getItemsCount() {
                    return getReviews().size();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getReviews().subList(getPageFirstItem(), getPageFirstItem() + getPageSize()));
                }
                
            };
        }
        return pagination;
    }
    
    public DataModel getDataModel(){
        if (datamodel == null)
            datamodel = getPagination().createPageDataModel();
        return datamodel;
    }
    
    public String next(){
        //TODO
        return null;
    }
    
    public String previous(){
        //TODO
        return null;
    }
    
    
    
    //////////////Pagination Logic Above////////////////
    
    
    
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
        
        if (track == null)
            return new ArrayList<>();
        if (relatedTracks == null){
            relatedTracks = similarControl.getSimilarTracks(track);
        }
        return relatedTracks;
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
    
    public void setRating(Integer rating){
        this.rating = rating;
    }
    
    public Integer getRating(){
        return rating;
    }

    /**
     * 
     * @param track
     * @return 
     */
    public String getProperlayout(Track track){
        if(track == null){
            log.info("Track is null");
            return inCart;          
        }
            
        if(!track.getAvailable()){
            return unavailable;
        }
        else{
            return available;
        }
    }
    
    public boolean canBuy(){
        List<Track> tracksInCart = cart.getAllTracks();
        if (tracksInCart == null || tracksInCart.isEmpty())
            return true;
        
        if (tracksInCart.contains(track))
            return false;
        else
            return true;
        
    }
    
    public boolean isLoggedIn(){
        return loginControl.isLoggedIn();
    }
    
}
