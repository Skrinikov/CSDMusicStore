package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.controllers.AlbumJpaController;
import com.fractals.controllers.TrackJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * The class that is responsible for storing Shopping Card information
 * and backing the appropriate page.
 *
 * @author Aline Shulzhenko
 * @version 26/02/2017
 * @since 1.8
 */
@Named("cart")
@SessionScoped
public class ShoppingCart implements Serializable{
    private List<Album> albums = new ArrayList<>();
    private List<Track> tracks = new ArrayList<>();
    private static transient final java.util.logging.Logger log = java.util.logging.Logger.getLogger("ShoppingCart.class");

    //for test
    @Inject
    AlbumJpaController ajc;
    @Inject
    private TrackJpaController tjc;

    /**
     * Returns the list of all tracks in the shopping cart.
     * @return the list of all tracks in the shopping cart.
     */
    public List<Track> getAllTracks() {
        return tracks;
    }
    
    /**
     * Returns the list of all albums in the shopping cart.
     * @return the list of all albums in the shopping cart.
     */
    public List<Album> getAllAlbums() {
        return albums;
    }
    
    /**
     * Returns the number of all items in the shopping cart (tracks and albums).
     * @return the number of all items in the shopping cart.
     */
    public int totalItems() {
        return albums.size() + tracks.size();
    }
    
    //only for tests
    @PostConstruct
    public void init() {
        empty();
        albums.add(ajc.findAlbum(1));
        albums.add(ajc.findAlbum(2));
        tracks.add(tjc.findTrack(1));
        tracks.add(tjc.findTrack(13));
    }
    
    /**
     * Returns the list of all objects in the shopping cart, both albums and tracks.
     * @return the list of all objects in the shopping cart.
     */
    public List<Object> getAll() {
        List<Object> items = new ArrayList<>();
        for(Album a : albums)
            items.add(a);
        for(Track t : tracks)
            items.add(t);
        return items;
    }
    
    /**
     * Returns artist for the specified item (album or track).
     * @param item Album or Track whose Artist to return.
     * @return artist for the specified item.
     */
    public Artist getArtist(Object item) {
        if(item == null)
            return null;
        if(item instanceof Album)
            return ((Album) item).getArtist();
        if(item instanceof Track) {
            Album album = ((Track) item).getAlbum();
            return album!= null ? album.getArtist() : null;
        }
        return null;
    }
    
    /**
     * Returns cover location for the specified item (album or track).
     * @param item Album or Track whose cover file location to return.
     * @return cover location for the specified item.
     */
    public String getCover(Object item) {
        if(item == null)
            return null;
        if(item instanceof Album) {
            List<Track> items = ((Album) item).getTracks();
            return (items == null || items.isEmpty()) ? null : items.get(0).getCoverFile();
        }
        if(item instanceof Track)
            return ((Track) item).getCoverFile();
        return null;
    }
    
    /**
     * Returns the type of the object.
     * @param item An object (Album or Track), which type to return.
     * @return Album if it is an Album object; Track if it is a Track object;
     *         null if neither of those types are submitted.
     */
    public String getType(Object item) {
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
        if(item instanceof Album)
            return bundle.getString("album");
        if(item instanceof Track)
            return bundle.getString("track");
        return null;
    }
    
    /**
     * Returns the link to the view page of this object.
     * @param item An object (Album or Track), which view page link is requested.
     * @return  the link to the view page of this object.
     */
    public String getDetailsLink(Object item) {
        if(item instanceof Album)
            return "Album.xhtml";
        if(item instanceof Track)
            return "Track.xhtml";
        return null;
    }
    
    /**
     * Empties the shopping cart.
     */
    public void empty() {
        albums = new ArrayList<>();
        tracks =  new ArrayList<>();
    }
    
    /**
     * Adds a tracks or an albums to the shopping cart.
     * @param o An item to add to the shopping cart.
     */
    public void add(Object o) {
        if(o instanceof Album) {
            Album album = (Album)o;
            if(!albums.contains(album))
                albums.add(album);
        }
        else if(o instanceof Track) {
            Track track = (Track)o;
            Album album = track.getAlbum();
            if(!tracks.contains(track) && !albums.contains(album)) { 
                resolveTrack(track, album);                
            }
        }
            
    }
    
    /**
     * Removes the item from the shopping cart.
     * @param o the item to remove from the shopping cart.
     */
    public void remove(Object o) {
        if(o instanceof Album)
            albums.remove((Album)o);
        
        else if(o instanceof Track) 
            tracks.remove((Track)o);
    }
    
    /**
     * Returns true if the cart is empty; false otherwise.
     * @return true if the cart is empty; false otherwise.
     */
    public boolean isEmpty() {
        return albums.isEmpty() && tracks.isEmpty();
    }
    
    /**
     * Verifies if all tracks in the shopping cart do not form an album.
     * If it is the case, the whole album is added to the shopping cart instead.
     * @param track The track pending the addition to the shopping cart.
     * @param album The album of that track.
     */
    private void resolveTrack(Track track, Album album) {
        int total = album.getNumTracks();
        int counter = 0;
        for(Track t : tracks)
            if(t.getAlbum().equals(album))
                counter++;

        if(counter+1 == total) {
            deleteTracks(album);
            albums.add(album);
        }
        else
            tracks.add(track);
    }
    
    /**
     * Deletes all tracks from the shopping cart that correspond to the specific album.
     * @param album The album, which tracks are deleted from the shopping cart.
     */
    private void deleteTracks(Album album) {
        List<Track> toRemove = new ArrayList<>();
        for(Track t : tracks)
            if(t.getAlbum().equals(album))
                toRemove.add(t);
        
        tracks.removeAll(toRemove);
    }
    
}
