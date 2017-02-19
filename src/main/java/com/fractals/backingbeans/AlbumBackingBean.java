/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
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
 
    private String test2;
    public String getTest2(){ return test2;}
    public void setTest2(String s){test2 = s;}
    
    private Artist test;
    public Artist getTest(){return test;}
    public void setTest(Artist stuff){test=stuff;}
   
    private boolean editable = false;
    public void setEditable(boolean b) {editable = b;}
    public boolean getEditable() {return editable;}
    public void editable() throws Exception {setEditable(true);}
    public void uneditable() {setEditable(false);}

    private Album selectedAlbum;
    
    public Album getSelectedAlbum() {
        if(selectedAlbum == null)
            selectedAlbum = getFirst();   //A CHANGER
        return selectedAlbum;      
    }
    
    public void setSelectedAlbum(Album a) {
        selectedAlbum = a;
    }

    private Album getFirst() {return getAlbums().get(72);}

    @Inject
    private AlbumJpaController albumJpaController;  
    
    public List<Album> getAlbums() {
        return albumJpaController.findAlbumEntities();
    }

    public boolean isEmpty() {
        return albumJpaController.isEmpty();
    }

    public void create() throws Exception {
        selectedAlbum.setCreatedAt(LocalDateTime.now());
        selectedAlbum.setReleaseDate(LocalDate.now());//A CHANGER
        selectedAlbum.setArtist(getAlbums().get(0).getArtist());//A CHANGER
        albumJpaController.create(selectedAlbum);
    }
    
    public void edit() throws Exception{
        albumJpaController.edit(selectedAlbum);
    }
    
    public void delete() throws Exception{
        albumJpaController.destroy(selectedAlbum.getId()); 
        selectedAlbum = null;
    }
}
