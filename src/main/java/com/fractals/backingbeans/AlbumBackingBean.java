/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.controllers.AlbumJpaController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 *
 * @author 1710030
 */
@Named("theAlbums")
@RequestScoped
public class AlbumBackingBean {
 
    private Album selectedAlbum;
    
    
    private boolean editable = false;

    public void setEditable(boolean b) {
        editable = b;
    }

    public boolean getEditable() {
        return editable;
    }

    public void edit() {
        setEditable(true);
    }

    public void unedit() {
        setEditable(false);
    }

    public Album getSelectedAlbum() {
        if(selectedAlbum == null)
            selectedAlbum = new Album();   
        return selectedAlbum;
    }
    
    public void setSelectedAlbum(Album a) {
        selectedAlbum = a;
    }

    public Album getFirst() {
       return getAlbums().get(0);
    }

    @Inject
    private AlbumJpaController albumJpaController;

    public List<Album> getAlbums() {
        return albumJpaController.findAlbumEntities();
    }

    public boolean isEmpty() {
        return albumJpaController.isEmpty();
    }

    public void create() throws Exception {
        selectedAlbum.setReleaseDate(LocalDate.MAX);
        selectedAlbum.setCreatedAt(LocalDateTime.MAX);
        selectedAlbum.setArtist(getAlbums().get(0).getArtist());
        albumJpaController.create(selectedAlbum);
    }
}
