package com.fractals.controllers;

//import javax.ejb.Stateful;
import com.fractals.beans.Album;
import com.fractals.beans.Track;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * i did it
 * @author lynn
 */
@Named("ShoppingCart")
@SessionScoped
public class ShoppingCart implements Serializable{
    private List<Album> albums = new ArrayList<>();
    private List<Track> tracks = new ArrayList<>();
    
    public List<Track> getAllTracks() {
        return tracks;
    }
    
    public List<Album> getAllAlbums() {
        return albums;
    }
    
    public void empty() {
        albums = new ArrayList<>();
        tracks =  new ArrayList<>();
    }
    
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
    
    public void remove(Object o) {
        if(o instanceof Album)
            albums.remove((Album)o);
        
        else if(o instanceof Track) 
            tracks.remove((Track)o);
    }
    
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
    
    private void deleteTracks(Album album) {
        for(Track t : tracks)
            if(t.getAlbum().equals(album))
                tracks.remove(t);
    }
    
}
