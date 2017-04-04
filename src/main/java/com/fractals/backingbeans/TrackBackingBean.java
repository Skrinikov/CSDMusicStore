package com.fractals.backingbeans;

import com.fractals.beans.Track;
import com.fractals.controllers.ReviewJpaController;
import com.fractals.controllers.TrackJpaController;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
    private ReviewJpaController reviewJpaController;
    
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

    public void makeUneditable() {
        setEditable(false);
    }

    public void edit() throws Exception {
        if (selectedTrack.getAvailable() == false && selectedTrack.getRemovedAt() == null) 
            selectedTrack.setRemovedAt(LocalDateTime.now());
        if (selectedTrack.getAvailable() == true && selectedTrack.getRemovedAt() != null) 
            selectedTrack.setRemovedAt(null);    
        
        makeUneditable();
        trackJpaController.edit(selectedTrack);
    }

    public void delete() throws Exception {
        if(selectedTrack.getRemovedAt() == null){
          selectedTrack.setRemovedAt(LocalDateTime.now());
          selectedTrack.setAvailable(false);
          trackJpaController.edit(selectedTrack);  
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    ResourceBundle.getBundle("bundle").getString("track_already_removed")) );                    
    }

    public void create() throws Exception {
        createdTrack.setCreatedAt(LocalDateTime.now());
        trackJpaController.create(createdTrack);
        selectedTrack = createdTrack;
        createdTrack = null;
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
    
    private List<String> getAllCoverFiles(){
        ArrayList<String> allCovers = new ArrayList<>();
        for(Track t :getTracks())
            if(!allCovers.contains(t.getCoverFile()))
                allCovers.add(t.getCoverFile());
        return allCovers;
    }
    
    public List<String> suggestCover(String s){
        ArrayList<String> suggestions = new ArrayList<>();
        suggestions.add("default_cover.jpg");
        for(String cover : getAllCoverFiles())
            if(cover.toLowerCase().startsWith(s.toLowerCase()))
                suggestions.add(cover);
        return suggestions;
                
    }
    
    /**
     * Returns the count of reviews in a Track
     * 
     * @param Track
     * @return Count of reviews of a Track
     * @author Thai-Vu Nguyen
     */
    public Number getReviewCountPerTrack(Track t){
        return reviewJpaController.getReviewsCountPerTrack(t);
    }
    
    /**
     * Returns the total sales of a Track
     * 
     * @parem Track
     */
    public Number getTotalSalesPerTrack (Track t){
        return trackJpaController.getTotalSales(t);
    }
    
    public Number getTracksSoldIndivually (Track t){
        return trackJpaController.getTracksSoldAsIndividualTrack(t);
    }
    
    public Number getTracksSoldSoldAsPartOfAlbum (Track t){
        return trackJpaController.getTracksSoldAsPartOfAlbum(t);
    }
    
    
}
