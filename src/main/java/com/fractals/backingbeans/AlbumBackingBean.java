/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.controllers.AlbumJpaController;
import com.fractals.controllers.AlbumController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named("theAlbums")
@SessionScoped
public class AlbumBackingBean implements Serializable {

    private boolean editable = false;   
    private Album selectedAlbum, createdAlbum;
    
    @Inject
    private AlbumJpaController albumJpaController;
    
    @Inject   
    private AlbumController albumController;
    
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
        makeUneditable();
        albumJpaController.edit(selectedAlbum);
    }

    public void delete() throws Exception {
        selectedAlbum.setRemovedAt(LocalDateTime.now());
        edit();
    }
    
    public Album getCreatedAlbum() {
        if (createdAlbum == null) {
            createdAlbum = new Album();
        }
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

    public Number getTotalSalesByAlbum(Album album) {
        Number sales = albumJpaController.getTotalSales(album);
        return (sales != null) ? sales : 0;
    }

    public Number getTotalSales() {
        return albumController.getTotalSales(selectedAlbum);
    }
}
