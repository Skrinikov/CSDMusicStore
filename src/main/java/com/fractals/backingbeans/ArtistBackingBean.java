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
 * Class containing the methods and variables for managing Artists
 * @author MOUFFOK Sarah
 */
@Named("theArtists")
@SessionScoped
public class ArtistBackingBean implements Serializable {

    @Inject
    private ArtistJpaController artistJpaController;
    private Artist selectedArtist, createdArtist;
    /**
    * Boolean that indicates if the manager is editing or viewing the 
    * selectedArtist
    */
    private boolean editable = false;

    public Artist getSelectedArtist() {
        return selectedArtist;
    }

    public void setSelectedArtist(Artist a) {
        selectedArtist = a;
    }

    public Artist getCreatedArtist() {
        if (createdArtist == null) 
            createdArtist = new Artist();
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
    /**
     * @return all the existing artists
     */
    public List<Artist> getArtists() {
        return artistJpaController.findArtistEntities();
    }
    /**
     * @return whether there are any artists in the database
     */
    public boolean isEmpty() {
        return artistJpaController.isEmpty();
    }
    
    /**
     * Creates the createdArtist
     * @throws Exception 
     */
    public void create() throws Exception {
        artistJpaController.create(createdArtist);
        selectedArtist = createdArtist;
        createdArtist = null;
    }

    public void edit() throws Exception {
        makeUneditable();
        artistJpaController.edit(selectedArtist);
    }

    public int getPageCount(){
        return artistJpaController.getArtistCount() / 10;
    }
    
    /**
     * Suggests Artists whose name starts with String s for autocomplete tags
     * @param s
     * @return 
     */
      public List<Artist> suggest(String s) {
          ArrayList<Artist> similar = new ArrayList<>();
        for (Artist a : artistJpaController.findArtistEntities()) 
            if (a.getName().toLowerCase().startsWith(s.toLowerCase())) 
                similar.add(a);   
        return similar;
    }
}
