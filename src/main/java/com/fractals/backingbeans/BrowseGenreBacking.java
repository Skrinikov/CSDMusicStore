package com.fractals.backingbeans;

import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import com.fractals.controllers.ClientTrackingController;
import com.fractals.controllers.GenreJpaController;
import com.fractals.controllers.ReportsController;
import com.fractals.controllers.TrackJpaController;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;


/**
 * This class will be used to interact with the browse xhtml page and
 * and read tracks in each genre and display it to the user; 
 * 
 * @author Renuchan
 */
@Named("browseBacking")
@RequestScoped
public class BrowseGenreBacking {
    private Integer genreId;
    private static transient final java.util.logging.Logger log = java.util.logging.Logger.getLogger("BrowseGenreBacking.class");
    
    @Inject 
    private GenreJpaController gjc; 
    
    @Inject 
    private TrackJpaController tjc; 
    
    @Inject 
    private ClientTrackingController ctc; 
    
    @Inject 
    private ReportsController rc; 
    
    /**
     * This method will get all the genre in the database. 
     * 
     * @return           List of genre
     */
    public List<Genre> getAllGenre()
    {
        return gjc.findGenreEntities();
    }
    
    /**
     * 
     * This method will get all track for a given genre
     * 
     * @param genreId        Genre tracks should be apart of
     * @return               List of tracks in the genre provided 
     */
    public List<Track> getTracksByGenre(Genre genre)
    {
        return tjc.findTracksByGenre(genre, tjc.getTrackCount(), true);
    }   
   
    
    /**
     * This method will be used to send the user to the track details page
     * for the track clicked in the showcase. 
     * 
     * @param track 
     */
    public void trackDetails(Track track)
    {
        String path = "Track.xhtml?id=" + track.getId();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(path);
        } catch (IOException ex) {
            Logger.getLogger(BrowseGenreBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * This method will be used to suggest songs based on the data collected 
     * about the user. 
     * @return 
     */
    public List<Track> getRecommended()
    {
        List<Track> list = null;
        
        try{
            if(ctc.isCookiesEnabled() && ctc.isGenreSavedInCookie())           
                list = ctc.getTracks(20, true);           
        }catch(Exception ex){
             Logger.getLogger(SurveyClientBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //if the program wasn't capable of obtain anything about the user 
        //show popular songs
        
        if(list == null)
            list = getTopSellers();
        
        return list;
    }
    
    /**
     * Shows the user the best sellers from 2 months ago if 
     * @return 
     */
    public List<Track> getTopSellers()
    {
        //get last month
        LocalDateTime last2Month = LocalDateTime.now().minusMonths(2);
        LocalDateTime today = LocalDateTime.now();
        
        return rc.getTopTrackSellers(last2Month, today); 
  
    }
    
    /**
     * This method will load the genre to display all tracks for that genre. 
     */
    public String loadAllTrackForGenre(Genre genre)
    {
        String path = "browse_genreTracks?id=" + genre.getId();
        return path; 
    }

    
    /**
     * This method is used by the browse_genreTracks page to display 
     * all available tracks for a specific genre. 
     * @return 
     */
    public List<Track> getAllTracksForGenre()
    {
        Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
        
        
        //possiblilty of the user entering invalid values in query string
        try{
            //default value if an integer is invalid
            String idStr = params.get("id");
            if(idStr == null || !idStr.matches("^[1-9]+[0-9]*$"))
                return tjc.findTracksByGenre(gjc.findGenre(1), tjc.getTrackCount(), true);
            
            int id = Integer.parseInt(idStr);
            return tjc.findTracksByGenre(gjc.findGenre(id), tjc.getTrackCount(), true);
               
        }catch(IllegalArgumentException ex){
            Logger.getLogger(BrowseGenreBacking.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            Logger.getLogger(BrowseGenreBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getRecommended(); 
    }
    
    /**
     * Returns the name of the genre currently stored in the cookie.
     * @return the name of the genre currently stored in the cookie.
     */
    public String getCookieGenre() {
        Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
        String idStr = params.get("id");
        String genre;
        if(idStr == null || !idStr.matches("^[1-9]+[0-9]*$"))
            genre =  gjc.findGenre(1).getName();
        else
            genre = gjc.findGenre(Integer.parseInt(idStr)).getName();
        return Character.toUpperCase(genre.charAt(0))+genre.substring(1);
    }
    
    /**
     * Returns the id of the genre.
     * @return the id of the genre.
     */
    public Integer getGenreId() {
        return genreId;
    }

    /**
     * Sets the id of the genre.
     * @param genreId The id of the genre.
     */
    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
    
    /**
     * Verifies that the genre id is the valid one.
     */
    public void init() {     
        if (genreId == null || gjc.getGenreCount() < genreId) {
            log.info("genre id is invalid: " + genreId);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String outcome = "/error.xhtml";
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
        }
    }
 
}
