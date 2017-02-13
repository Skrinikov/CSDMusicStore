/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.controllers.TrackJpaController;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Backing bean for the Track client view
 * 
 * @author Thai-Vu Nguyen
 */
@Named("TrackCLBack")
@RequestScoped
public class TrackClientBacking implements Serializable {
    private Integer trackId;
    private Track track;
    
    @Inject
    private TrackJpaController trackControl;
    
    /**
     * Initialize the Track entity based on the trackId
     */
    public void init(){
        track = trackControl.findTrack(trackId);
    }
    
    public void setTrackId(Integer trackId){
        this.trackId = trackId;
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
