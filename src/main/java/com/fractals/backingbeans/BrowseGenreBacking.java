/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import com.fractals.controllers.ClientTrackingController;
import com.fractals.controllers.GenreJpaController;
import com.fractals.controllers.ReportsController;
import com.fractals.controllers.TrackJpaController;
import java.io.IOException;
import java.util.ArrayList;
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
 *  This class will be used to interact with the browse xhtml page and
 *  and read tracks in each genre and display it to the user; 
 * 
 * @author Renuchan
 */
@Named("browseBacking")
@RequestScoped
public class BrowseGenreBacking {
    
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
        int numTracks = 4;
        return tjc.findTracksByGenre(genre, numTracks, true);

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
            
            int id = Integer.parseInt(params.get("id"));
            return tjc.findTracksByGenre(gjc.findGenre(id), tjc.getTrackCount(), true);
               
        }catch(IllegalArgumentException ex){
            Logger.getLogger(BrowseGenreBacking.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            Logger.getLogger(BrowseGenreBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getRecommended(); 
    }
 
}
