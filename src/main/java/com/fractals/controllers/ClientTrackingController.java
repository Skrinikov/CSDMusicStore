package com.fractals.controllers;

import com.fractals.backingbeans.exceptions.NonexistentEntityException;
import com.fractals.backingbeans.exceptions.RollbackFailureException;
import com.fractals.beans.Album;
import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 * Controller used to manage the track of what the User is visiting
 * @author Thai-Vu Nguyen
 */
@Named("clienttracking")
@RequestScoped
public class ClientTrackingController {
    
    private static final Logger LOG = Logger.getLogger("CookiesController.class");

    
    @Inject
    private GenreJpaController genreControl;
    
    @Inject
    private AlbumJpaController albumControl;
    
    @Inject
    private TrackJpaController trackControl;
    
    @Inject
    private UserJpaController userControl;
    
    @Inject
    private LoginController loginControl;
    
    /**
     * Saves the last visited genre
     * @param genre Genre
     */
    public void saveGenre(Genre genre){
        if(loginControl.isLoggedIn()){
            try {
                saveGenreToDB(loginControl.getCurrentUser(), genre);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(ClientTrackingController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ClientTrackingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        registerGenreToCookies(genre);
    }
    
    /**
     * Returns the last visited genre
     * @return Genre
     */
    public Genre getGenre(){
        if (loginControl.isLoggedIn())
            return loginControl.getCurrentUser().getLastGenre();
        else
            return getGenreFromCookie();
    }
    
    /**
     * Stores the last genre of a Track or Album page visited by the user
     * @param genre 
     */
    private void registerGenreToCookies(Genre genre){
        LOG.info(genre.toString());
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie("LastGenre", genre.getId().toString(), null);
    }
    
    /**
     * 
     * Gets the last Genre of Track or Album page visited by a user 
     * @return Genre
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
    
    /**
     * Checks if the genre is saved
     * @return boolean
     */
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
    
    /**
     * Gets a list of albums based on the last visited genre
     * @param count
     * @param random
     * @return List of Album
     */
    public List<Album> getAlbums (int count, boolean random){
        Genre genre = getGenre();
        
        List<Album> albums = albumControl.findAlbumsByGenre(genre, count, random);
        
        return albums;
    }
    
    /**
     * Gets a list of tracks based on the last visited genre
     * @param count
     * @param random
     * @return List of Track
     */
    public List<Track> getTracks(int count, boolean random){
        Genre genre = getGenre();
        
        List<Track> tracks = trackControl.findTracksByGenre(genre, count, random);
        return tracks;
    }
    
    /**
     * Checks if the browser supports cookies
     * @return true if browser supports cookies, false if it doesn't
     */
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
    
    /**
     * Module to save the Genre to the database
     * @param user User
     * @param genre Genre
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    private void saveGenreToDB(User user, Genre genre) throws NonexistentEntityException, RollbackFailureException, Exception{
        user.setLastGenre(genre);
        userControl.edit(user);
    }
    
}
