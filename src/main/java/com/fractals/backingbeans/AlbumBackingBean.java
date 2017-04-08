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
 *
 * @author MOUFFOK Sarah
 */
@Named("theAlbums")
@SessionScoped
public class AlbumBackingBean implements Serializable {
    
    @Inject
    private AlbumJpaController albumJpaController;
    private boolean editable = false;   
    private Album selectedAlbum, createdAlbum;
    
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

    public List<Album> getAlbums() {
        return albumJpaController.findAlbumEntities();
    }

    public boolean isEmpty() {
        return albumJpaController.isEmpty();
    }

    public void edit() throws Exception {
        if(selectedAlbum.getAvailable() == false && selectedAlbum.getRemovedAt() == null)
            selectedAlbum.setRemovedAt(LocalDateTime.now());
        if(selectedAlbum.getAvailable() == true && selectedAlbum.getRemovedAt() != null)
            selectedAlbum.setRemovedAt(null);
        
        makeUneditable();
        albumJpaController.edit(selectedAlbum);
    }

    public void delete() throws Exception {
        if(selectedAlbum.getRemovedAt() == null){
          selectedAlbum.setRemovedAt(LocalDateTime.now());
          selectedAlbum.setAvailable(false);
          albumJpaController.edit(selectedAlbum);  
        }
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("bundle").getString("album_already_removed")) );                              
    }
    
    public Album getCreatedAlbum() {
        if (createdAlbum == null) 
            createdAlbum = new Album();
        return createdAlbum;
    }

    public void setCreatedAlbum(Album a) {
        createdAlbum = a;
    }

    public void create() throws Exception {
        createdAlbum.setCreatedAt(LocalDateTime.now());
        albumJpaController.create(createdAlbum);
        selectedAlbum = createdAlbum;
        createdAlbum = null;
     
    }
    
    public int getPageCount(){
        return albumJpaController.getAlbumCount() / 10;
    }
    
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
     * @return Thai-Vu Nguyen
     */
    public Number getAlbumsSold(Album album){
        Number number = albumJpaController.getAlbumsSold(album);
        return (number != null)? number : 0;
    }
    
    /**
     * Total sales of an Album
     * @param album
     * @return Total sales of an Album
     * @return Thai-Vu Nguyen
     */
    public Number getAlbumsSales(Album album){
        Number number = albumJpaController.getTotalSales(album);
        return (number != null)? number : 0;
    }
}
