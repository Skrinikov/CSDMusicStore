/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Review;
import com.fractals.beans.Track;
import com.fractals.controllers.ReviewsWebController;
import com.fractals.controllers.SimilarTracksController;
import com.fractals.controllers.TrackJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Backing bean for the Track client view
 * 
 * @author Thai-Vu Nguyen
 */
@Named("trackCLBack")
@RequestScoped
public class TrackClientBacking implements Serializable {
    private Integer trackId;
    private Track track;
    private Integer rating;
    private String review;
    
    @Inject
    private TrackJpaController trackControl;
    
    @Inject
    private ReviewsWebController reviewsControl;
    
    @Inject
    private SimilarTracksController similarControl;
    
    /**
     * Initialize the Track entity based on the trackId
     */
    public void init(){
        track = trackControl.findTrack(trackId);
    }
    
    public void addReview(){
        //Won't work because we have to deal with the user
        this.reviewsControl.addReview(track, review, null, rating.intValue());
    }
    
    public void setTrackId(Integer trackId){
        this.trackId = trackId;
    }
    
    public String getArtists(){
        if (track == null)
            return "";
        List<Artist> artists = track.getArtists();
        if (artists == null || artists.isEmpty()){
            return "";
        }
        
        String formattedArtists = "";
        for (int i = 0; i < artists.size(); i++){
            formattedArtists += artists.get(i).getName();
            if (i < artists.size() - 1){
                formattedArtists += ", ";
            }
        }
        return formattedArtists;
        
    }
    
    public List<Track> getSimilarTracks(){
        return similarControl.getSimilarTracks(track);
    }
    
    public List<Review> getReviews(){
        return (track != null)?track.getReviews():(new ArrayList<Review>());
    }
    
    public Integer getTrackId(){
        return this.trackId;
    }
    
    public Track getTrack(){
        return this.track;
    }
    
    public Album getAlbum(){
        return (track != null)?track.getAlbum():(new Album());
    }
    
    public String getTitle(){
        return (track != null)?track.getTitle():"";
    }
    
    public boolean isAvailable(){
        return (track != null)?track.getAvailable():false;
    }
    
    public boolean isIndividual(){
        return (track != null)?track.getIsIndividual():false;
    }
    
    public double getListedPrice(){
        return (track != null)?track.getListPrice():0.00;
    }
    
    public double getSalesPrice(){
        return (track != null)?track.getSalePrice():0.00;
    }
    
    public String getDuration(){
        return (track != null)?track.getDuration():"";
    }
    
    public String getWriter(){
        return (track != null)?track.getSongwriter():"";
    }
    
    
}
