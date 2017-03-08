package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 *
 * @author Thai-Vu Nguyen
 */
@Named("cookiesControl")
@RequestScoped
public class ClientTrackingController {
    
    private static final Logger LOG = Logger.getLogger("CookiesController.class");

    
    @Inject
    private GenreJpaController genreControl;
    
    @Inject
    private AlbumJpaController albumControl;
    
    @Inject
    private TrackJpaController trackControl;
    
    /**
     * Stores the last genre of a Track or Album page visited by the user
     * @param genre 
     */
    public void registerGenreToCookies(Genre genre){
        LOG.info(genre.toString());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie("LastGenre", genre.getId().toString(), null);
    }
    
    /**
     * 
     * Gets the last Genre of Track or Album page visited by a user 
     */
    public Genre getGenreFromCookie(){
        Map<String, Object> cookies = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        
        Cookie cookie = (Cookie) cookies.get("LastGenre");
        LOG.info("Genre ID from the cookie" + cookie.getValue());
        Integer genre_id = Integer.getInteger(cookie.getValue());
        return genreControl.findGenre(genre_id);
    }
    
    public boolean isGenreSaved(){
        Map<String, Object> cookies = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        
        Cookie cookie = (Cookie) cookies.get("LastGenre");
        if (cookie == null){
            return false;
        }
        else
            return true;
    }
    
    public List<Album> getAlbumsFromCookies (int count, boolean random){
        Genre genre = getGenreFromCookie();
        
        List<Album> albums = albumControl.findAlbumsByGenre(genre, count, random);
        
        return albums;
    }
    
    public List<Track> getTracksFromCookies(int count, boolean random){
        Genre genre = getGenreFromCookie();
        
        List<Track> tracks = trackControl.findTracksByGenre(genre, count, random);
        return tracks;
    }
    
    public boolean isCookiesEnabled(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie("CookieEnable", "Testing", null);
        
        Map<String, Object> cookies = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        
        
        if (cookies == null){
            return false;
        }
        
        if(cookies.containsKey("CookieEnable")){
            return true;
        }
        return false;
    }
}
