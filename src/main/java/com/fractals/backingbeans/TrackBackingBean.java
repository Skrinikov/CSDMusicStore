/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Track;
import com.fractals.controllers.TrackJpaController;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("theTracks")
@RequestScoped
public class TrackBackingBean {
    
    @Inject
    private TrackJpaController trackJpaController;
    
    public List<Track> getTracks(){
        return trackJpaController.findTrackEntities();
    }
    
    public boolean isEmpty(){
       return trackJpaController.isEmpty();
    }
       
    private Track createdTrack;
    public Track getCreatedTrack(){
        if(createdTrack == null)
            createdTrack = new Track();
        return createdTrack;
    }
    public void setCreatedTrack(Track t){createdTrack = t;}
    
    private Track selectedTrack;
    public Track getSelectedTrack(){return selectedTrack;}
    public void setSelectedTrack(Track t){selectedTrack = t;}
 
    private boolean editable = false;
    public boolean getEditable() {return editable;}
    public void setEditable(boolean b) {editable = b;}
    public void makeEditable(){setEditable(true);};
    public void makeUneditable(){setEditable(false);}
    
       public String create() throws Exception {
        trackJpaController.create(createdTrack);
        selectedTrack = createdTrack;
        createdTrack = null;
        return "/management/track/tracksList.xhtml";
    }
       
    public String edit()throws Exception {
        trackJpaController.edit(selectedTrack);
        return "/management/track/tracksList.xhtml";
    }

    public void delete() throws Exception {
        //trackJpaController.destroy(selectedTrack.getId());
        selectedTrack.setRemovedAt(LocalDateTime.now());
        edit();
    }
    
}
