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
 * @author 1710030
 */
@Named("theTracks")
@SessionScoped
public class TrackBackingBean implements Serializable {
    
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

       
    public String edit()throws Exception {
        trackJpaController.edit(selectedTrack);
        return "/management/track/tracksList.xhtml";
    }

    public void delete() throws Exception {
        //trackJpaController.destroy(selectedTrack.getId());
        selectedTrack.setRemovedAt(LocalDateTime.now());
        edit();
    }
    
    
        
    
    @Inject private AlbumJpaController album;
    @Inject private ArtistJpaController artist;
    @Inject private GenreJpaController genre;
    
       public String create() throws Exception {
           
        ArrayList<Artist> a = new ArrayList();
        a.add(artist.findArtistEntities().get(0));
        createdTrack.setArtists(a); 
        createdTrack.setAlbum(album.findAlbumEntities().get(0));
        createdTrack.setGenre(genre.findGenreEntities().get(0));
        //A CHANGER
        
        createdTrack.setCreatedAt(LocalDateTime.now());
        
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getCoverFile() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getDuration() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getSongwriter() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getTitle() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getAlbum()== null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getAlbumNum() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getArtists() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getAvailable() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getCostPrice() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getCreatedAt() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getGenre() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getIsIndividual() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getListPrice() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getOrderItems() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getRemovedAt() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getSalePrice() == null);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + createdTrack.getReviews() == null);
    
        
        
        
        trackJpaController.create(createdTrack);
        selectedTrack = createdTrack;
        createdTrack = null;
        return "/management/track/tracksViewEdit.xhtml";
    }
       
}
