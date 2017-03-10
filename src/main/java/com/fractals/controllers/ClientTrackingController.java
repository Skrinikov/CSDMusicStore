package com.fractals.controllers;

import com.fractals.backingbeans.LoginBacking;
import com.fractals.backingbeans.exceptions.NonexistentEntityException;
import com.fractals.backingbeans.exceptions.RollbackFailureException;
import com.fractals.beans.Album;
import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
public class ClientTrackingController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger("CookiesController.class");
    private static final String GENRE_KEY = "genreID";

    
    @Inject
    private GenreJpaController genreControl;
    
    @Inject
    private AlbumJpaController albumControl;
    
    @Inject
    private TrackJpaController trackControl;
    
    @Inject
    private UserJpaController userControl;
    
    @Inject
    private LoginBacking loginControl;
    
    /**
     * Saves the last visited genre
     * @param genre Genre
     */
    public void saveGenre(Genre genre, FacesContext context){
        if(loginControl.isLoggedIn()){
            try {
                saveGenreToDB(loginControl.getCurrentUser(), genre);
            } catch (Exception ex) {
                Logger.getLogger(ClientTrackingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        registerGenreToCookies(genre, context);
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
    private void registerGenreToCookies(Genre genre, FacesContext context){
        LOG.info("Registering " + genre.toString() + "'s ID of "+ genre.getId().toString() + " to Cookie");
        
        context.getExternalContext().addResponseCookie(GENRE_KEY, genre.getId().intValue() + "", null);
        
        Map<String, Object> cookies = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        
        LOG.info("Is cookie saved? " + cookies.containsKey(GENRE_KEY));
        
        
    }
    
    /**
     * 
     * Gets the last Genre of Track or Album page visited by a user 
     * @return Genre
     */
    private Genre getGenreFromCookie(){
        Map<String, Object> cookies = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        
        Cookie cookie = (Cookie) cookies.get(GENRE_KEY);
        LOG.info("Genre ID from the cookie" + cookie.getValue());
        Integer genre_id = Integer.getInteger(cookie.getValue());
        return genreControl.findGenre(genre_id);
    }
    
    /**
     * Checks if the genre is saved
     * @return boolean
     */
    public boolean isGenreSaved(){
        if (loginControl.isLoggedIn())
            return isGenreSavedInDB(loginControl.getCurrentUser());
        else
            return isGenreSavedInCookie();
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
    
    public boolean isGenreSavedInCookie(){
        Map<String, Object> cookies = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        return cookies.containsKey(GENRE_KEY);
        
    }
    
    public boolean isGenreSavedInDB(User user){
        return user.getLastGenre() != null;
    }
    
    public int cookieSize(){
        Map<String, Object> cookies = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        int num = cookies.size();
        return num;
    }
    
}
