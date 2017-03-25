/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Artist;
import com.fractals.controllers.ArtistJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("theArtists")
@SessionScoped
public class ArtistBackingBean implements Serializable {

    @Inject
    private ArtistJpaController artistJpaController;
    private Artist selectedArtist, createdArtist;
    private boolean editable = false;

    public Artist getSelectedArtist() {
        return selectedArtist;
    }

    public void setSelectedArtist(Artist a) {
        selectedArtist = a;
    }

    public Artist getCreatedArtist() {
        if (createdArtist == null) {
            createdArtist = new Artist();
        }
        return createdArtist;
    }

    public void setCreatedArtist(Artist a) {
        createdArtist = a;
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

    public List<Artist> getArtists() {
        return artistJpaController.findArtistEntities();
    }

    public boolean isEmpty() {
        return artistJpaController.isEmpty();
    }

    public void create() throws Exception {
        artistJpaController.create(createdArtist);
        selectedArtist = createdArtist;
        createdArtist = null;
    }

    public void edit() throws Exception {
        makeUneditable();
        artistJpaController.edit(selectedArtist);
    }

    /*public void delete() throws Exception {
        artistJpaController.destroy(selectedArtist.getId());
    }*/
    
    public int getPageCount(){
        return artistJpaController.getArtistCount() / 10;
    }
    
      public List<Artist> suggest(String s) {
          ArrayList<Artist> similar = new ArrayList<>();
        for (Artist a : artistJpaController.findArtistEntities()) 
            if (a.getName().toLowerCase().startsWith(s.toLowerCase())) 
                similar.add(a);   
        return similar;
    }
}
