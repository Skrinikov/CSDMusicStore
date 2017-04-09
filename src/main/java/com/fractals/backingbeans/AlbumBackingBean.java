package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.controllers.AlbumJpaController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class containing the methods and variables for managing Albums
 * @author MOUFFOK Sarah
 */

@Named("theAlbums")
@SessionScoped
public class AlbumBackingBean implements Serializable {
    
    @Inject
    private AlbumJpaController albumJpaController;
    private Album selectedAlbum, createdAlbum;
    /**
    * Boolean that indicates if the manager is editing or viewing the selectedAlbum
    */
    private boolean editable = false;  
    
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

    public Album getSelectedAlbum() {
        return selectedAlbum;
    }
    
    public void setSelectedAlbum(Album a) {
        selectedAlbum = a;
        makeUneditable();
    }
    
    /**
     * @return a list of all the existing albums
     */
    public List<Album> getAlbums() {
        return albumJpaController.findAlbumEntities();
    }
    
    /**
     * @return whether there are any albums in the database
     */
    public boolean isEmpty() {
        return albumJpaController.isEmpty();
    }
    /**
     * Edits the selectedAlbum and updates removal date if necessary
     * @throws Exception 
     */
    public void edit() throws Exception {
        if(!selectedAlbum.getAvailable() && selectedAlbum.getRemovedAt() == null)
            selectedAlbum.setRemovedAt(LocalDateTime.now());
        if(selectedAlbum.getAvailable() && selectedAlbum.getRemovedAt() != null)
            selectedAlbum.setRemovedAt(null);
        makeUneditable();
        albumJpaController.edit(selectedAlbum);
    }

    /**
     * Deletes selectedAlbum by updating removed and available field
     * @throws Exception 
     */
    public void delete() throws Exception {
        if(selectedAlbum.getRemovedAt() == null){
          selectedAlbum.setRemovedAt(LocalDateTime.now());
          selectedAlbum.setAvailable(false);
          albumJpaController.edit(selectedAlbum);  
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("bundle").getString("album_already_removed")) );                              
    }
    /**
     * @return the Created Album
     */
    public Album getCreatedAlbum() {
        if (createdAlbum == null) 
            createdAlbum = new Album();
        return createdAlbum;
    }

    public void setCreatedAlbum(Album a) {
        createdAlbum = a;
    }

    /**
     * Creates the createdAlbums and sets created date 
     * @throws Exception 
     */
    public void create() throws Exception {
        createdAlbum.setCreatedAt(LocalDateTime.now());
        albumJpaController.create(createdAlbum);
        selectedAlbum = createdAlbum;
        createdAlbum = null;
     
    }
    
    /**
     * Returns the last page of the datatable to be able to switch 
     * over to that page after the creation of an album
     * @return number of pages of a 10 row datatable
     */
    public int getPageCount(){
        return albumJpaController.getAlbumCount() / 10;
    }
    
    /**
     * Suggests albums who start by String s for autocomplete tags
     * @param s, the search
     * @return 
     */
     public List<Album> suggest(String s) {
        ArrayList<Album> similar = new ArrayList<>();
        for (Album a : albumJpaController.findAlbumEntities()) 
            if (a.getTitle().toLowerCase().startsWith(s.toLowerCase())) 
                similar.add(a);   
        return similar;
    }
     
     /**
     * Amount of copies sold by an album
     * @param album
     * @author Thai-Vu Nguyen
     */
    public Number getAlbumsSold(Album album){
        Number number = albumJpaController.getAlbumsSold(album);
        return (number != null)? number : 0;
    }
    
    public Number getAlbumsSales(Album album){
        Number number = albumJpaController.getTotalSales(album);
        return (number != null)? number : 0;
    }
}
