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
    public String getTest2() {return test2;}
    public void setTest2(String s) {test2 = s;}

    private Artist test;
    public Artist getTest() {return test;}
    public void setTest(Artist stuff) {
        test = stuff;
        System.out.println("test = " + test.getName());
    }

    private boolean editable = false;
    public boolean getEditable() {return editable;}
    public void setEditable(boolean b) {editable = b;}
    public void editable(){setEditable(true);};
    public void uneditable(){setEditable(false);}
    
    /*TRYING TO DEAL WITH THE RESTRICTIONS OF REQUEST SCOPE
    public String editable(Album a) {
        setSelectedAlbum(a);
        setEditable(true);
        return "/managment/albumsViewEdit.xhtml"; 
    }
    public String uneditable(Album a) {
        setSelectedAlbum(a);
        setEditable(false);
        return "/managment/albumsViewEdit.xhtml";
    }
    */

    private Album selectedAlbum;
    public Album getSelectedAlbum() {return selectedAlbum;}
    public void setSelectedAlbum(Album a) {selectedAlbum = a;}
    public boolean selection(){return selectedAlbum == null;}
    
    private Album getFirst() {return getAlbums().get(72);}//TO TEST DELETE

    @Inject
    private AlbumJpaController albumJpaController;
    public List<Album> getAlbums() {return albumJpaController.findAlbumEntities();}
    public boolean isEmpty() {return albumJpaController.isEmpty();}

    public void edit() throws Exception {
        albumJpaController.edit(selectedAlbum);
    }

    public String delete() throws Exception {
        albumJpaController.destroy(selectedAlbum.getId());
        selectedAlbum = null;
        return "/managment/albumsList.xhtml"; 
    }
    
    private Album createdAlbum;

    public Album getCreatedAlbum() {
        if (createdAlbum == null)
            createdAlbum = new Album();
        return createdAlbum;
    }

    public void setCreatedAlbum(Album a) {createdAlbum = a;}

    public String create() throws Exception {
        createdAlbum.setCreatedAt(LocalDateTime.now());
        createdAlbum.setReleaseDate(LocalDate.now());//A CHANGER
        createdAlbum.setArtist(getAlbums().get(0).getArtist());//A CHANGER
        albumJpaController.create(createdAlbum);
        selectedAlbum = createdAlbum;
        return "/managment/albumsViewEdit.xhtml";
    }
}
