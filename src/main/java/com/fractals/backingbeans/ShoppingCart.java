package com.fractals.backingbeans;

//import javax.ejb.Stateful;
import com.fractals.beans.Album;
import com.fractals.beans.Track;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * The logic for shopping cart.
 *
 * @author Aline Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
@Named("ShoppingCart")
@SessionScoped
public class ShoppingCart implements Serializable{
    private List<Album> albums = new ArrayList<>();
    private List<Track> tracks = new ArrayList<>();
    
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
        for(Track t : tracks)
            if(t.getAlbum().equals(album))
                tracks.remove(t);
    }
    
}
