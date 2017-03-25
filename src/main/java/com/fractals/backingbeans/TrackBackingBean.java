/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.controllers.AlbumJpaController;
import com.fractals.controllers.ArtistJpaController;
import com.fractals.controllers.GenreJpaController;
import com.fractals.controllers.TrackController;
import com.fractals.controllers.TrackJpaController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named("theTracks")
@SessionScoped
public class TrackBackingBean implements Serializable {

    @Inject
    private TrackJpaController trackJpaController;
    @Inject
    private TrackController trackController;
    private boolean editable = false;
    private Track createdTrack, selectedTrack;

    public List<Track> getTracks() {
        return trackJpaController.findTrackEntities();
    }

    public boolean isEmpty() {
        return trackJpaController.isEmpty();
    }

    public Track getCreatedTrack() {
        if (createdTrack == null) {
            createdTrack = new Track();
        }
        return createdTrack;
    }

    public void setCreatedTrack(Track t) {
        createdTrack = t;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(Track t) {
        selectedTrack = t;
        makeUneditable();
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean b) {
        editable = b;
    }

    public void makeEditable() {
        setEditable(true);
    }

    ;
    public void makeUneditable() {
        setEditable(false);
    }

    public void edit() throws Exception {
        makeUneditable();
        trackJpaController.edit(selectedTrack);
    }

    public void delete() throws Exception {
        selectedTrack.setRemovedAt(LocalDateTime.now());
        selectedTrack.setAvailable(false);
        edit();
    }

    public void create() throws Exception {
        createdTrack.setCreatedAt(LocalDateTime.now());
        trackJpaController.create(createdTrack);
        selectedTrack = createdTrack;
        createdTrack = null;
    }

    public Number getTotalSalesByTrack(Track t) {
        return trackController.getTotalSales(t);
    }

    public Number getTotalSales() {
        return getTotalSalesByTrack(selectedTrack);
    }

    public int getPageCount() {
        return trackJpaController.getTrackCount() / 10;
    }
    
    /**
     * The number of copies sold by a track
     * @param track
     * @return number of copies sold
     */
    public Number getTracksSold (Track track){
        Number number = trackJpaController.getTracksSold(track);
        
        return (number != null) ? number : 0;
    }

}
