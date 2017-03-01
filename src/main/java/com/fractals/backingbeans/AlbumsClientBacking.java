package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.controllers.AlbumJpaController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Backing bean for the client side Album page
 * 
 * @author Thai-Vu Nguyen, Danieil Skrinikov
 */
@Named("albumsCLBack")
@RequestScoped
public class AlbumsClientBacking {
    private Album album;
    private Integer albumId;
    private List<Album> similarAlbums;
    private boolean isLoaded = false;
    
    @Inject
    AlbumJpaController albumControl;
    
    @Inject
    ShoppingCart shopControl;
    
    //Initializes the Album entity
    public void init(){
         album = albumControl.findAlbum(albumId);
         similarAlbums = albumControl.getSimilarAlbums(album, 3);
         isLoaded=true;
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

    public List<Album> getSimilarAlbums() {
        return similarAlbums;
    }

    public void setSimilarAlbums(List<Album> similarAlbums) {
        this.similarAlbums = similarAlbums;
    }     

    public boolean isIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }
       
    
    /**
     * Returns the real price for the album. If there is a sale, returns the sale price, if not returns the list price.
     * 
     * @param album Album to fetch the information from.
     * @return Real price for the album.
     */
    public double getPrice(Album album){
        return (album.getSalePrice() <= 0) ? album.getListPrice() : album.getSalePrice();
    }
    
    /**
     * returns the cover image name of the album.
     * @return 
     */
    public String getAlbumCover(){   
        return album.getTracks().get(0).getCoverFile();     
    }
    
    public String getAlbumCover(Album album){
        return album.getTracks().get(0).getCoverFile();
    }

}
