/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.controllers.AlbumJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * NOT PART OF PRODUCTION CODE
 * Controller to a page whose sole purpose is to display all the albums for me
 * to test if i can nagivate from a list of Albums to an Album page
 * 
 * @author Thai-Vu Nguyen
 */
@Named("displayer")
@SessionScoped
public class DisplayerController implements Serializable{
    @Inject
    private AlbumJpaController albumControl;
    
    public List<Album> getAllAlbums(){
        return albumControl.findAlbumEntities();
    }
    
    public boolean isEmpty(){
        return albumControl.isEmpty();
    }
    
    public String test(){
        return "The number of albums are " + albumControl.getAlbumCount();
    }
}
