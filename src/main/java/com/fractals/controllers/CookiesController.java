package com.fractals.controllers;

import com.fractals.beans.Genre;
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
public class CookiesController {
    
    private static final Logger LOG = Logger.getLogger("CookiesController.class");

    
    @Inject
    private GenreJpaController genreControl;
    
    
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
    
    private boolean checkBrowser(){
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
