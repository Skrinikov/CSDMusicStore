/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.controllers.AlbumJpaController;
import com.fractals.controllers.ShoppingCart;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Backing bean for the client side Album page
 * 
 * @author Thai-Vu Nguyen
 */
@Named("albumsCLBack")
@RequestScoped
public class AlbumsClientBacking {
    private Album album;
    private Integer albumId;
    
    @Inject
    AlbumJpaController albumControl;
    
    @Inject
    ShoppingCart shopControl;
    
    //Initializes the Album entity
    public void init(){
         album = albumControl.findAlbum(albumId);
    }
    
    /**
     * Function to add the current instance of the album to the shopping
     */
    public void addAlbumToCart(){
        shopControl.add(album);
    }
    
    public Album getAlbum(){
        return album;
    }
    
    public Integer getAlbumId(){
        return this.albumId;
    }
    
    public void setAlbumId(Integer albumId){
        this.albumId = albumId;
    }
    
    public String getTitle(){
        return (album != null)?album.getTitle():"";
    }
    
    public Artist getArtist(){      
        return (album != null)?album.getArtist():(new Artist());
    }
    
    public boolean isAvailable(){
        return (album != null)?album.getAvailable():false;
    }
    
    public double getCostPrice(){
        return (album != null)?album.getCostPrice():0.00;
    }
    
    public double getListPrice(){
        return (album != null)?album.getListPrice():0.00;
    }
    
    public double getSalesPrice(){
        return (album != null)?album.getSalePrice():0.00;
    }
    
    public LocalDateTime getCreatedAt(){
        return (album != null)?album.getCreatedAt():null;
    }
    
    public List<Track> getTracks(){
        return (album != null)?album.getTracks():(new ArrayList<Track>());
    }
    
    public String getLabel(){
        return (album != null)?album.getRecordLabel():"";
    }
     
}
