/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Artist;
import com.fractals.controllers.ArtistJpaController;
import java.io.Serializable;
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
public class ArtistBackingBean implements Serializable{
    
    private Artist selectedArtist;
    public Artist getSelectedArtist(){ return selectedArtist;}
    public void setSelectedArtist(Artist a){ selectedArtist = a;}
    
    private Artist createdArtist;
    public Artist getCreatedArtist(){
        if(createdArtist == null)
            createdArtist = new Artist();
        return createdArtist;
    }
    public void setCreatedArtist(Artist a){ createdArtist = a;} 
        
    private boolean editable = false;
    public boolean getEditable() {return editable;}
    public void setEditable(boolean b) {editable = b;}
    public void makeEditable(){setEditable(true);};
    public void makeUneditable(){setEditable(false);}
    
    @Inject
    private ArtistJpaController artistJpaController;

    public List<Artist> getArtists() {
        return artistJpaController.findArtistEntities();
    }

    public boolean isEmpty() {
        return artistJpaController.isEmpty();
    }
    
    
       public String create() throws Exception {
        artistJpaController.create(createdArtist);
        selectedArtist = createdArtist;
        createdArtist = null;
        return "/management/artist/artistsList.xhtml";
    }
       
       public String edit()throws Exception {
           artistJpaController.edit(selectedArtist);
           return "/management/artist/artistsList.xhtml";
       }
       
       public void delete() throws Exception {
           artistJpaController.destroy(selectedArtist.getId());
       }

}
