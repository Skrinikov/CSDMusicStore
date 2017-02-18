package com.fractals.backingbeans;

import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import com.fractals.controllers.GenreJpaController;
import com.fractals.controllers.RssFeedController;
import com.fractals.controllers.TrackJpaController;
import com.fractals.rss.FeedMessage;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This bean is used by the index page to fetch important data from various controllers.
 * 
 * @author Danieil Skrinikov
 * @version 1.0.0
 * @since 2017-02-17
 */
@Named("indexBacking")
@RequestScoped
public class IndexBacking {
    
    // Controllers
    @Inject
    private RssFeedController rss;
    @Inject
    private GenreJpaController genre;
    @Inject
    private TrackJpaController tracks;
    
    // Class Variables
    private List<FeedMessage> rssMsgs;
    private List<Genre> genres;
    private List<Track> newTracks;
    
    /**
     * Generates all components which are needed to display the page.
     */
    @PostConstruct
    public void init(){
        rssMsgs = rss.getVisibleFeed();
        genres = genre.findGenreEntities();
        newTracks = tracks.getMostRecentTracks(3);
    }
    
    /* Getters and Setters */

    public List<FeedMessage> getRssMsgs() {
        return rssMsgs;
    }

    public void setRssMsgs(List<FeedMessage> rssMsgs) {
        this.rssMsgs = rssMsgs;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Track> getNewTracks() {
        return newTracks;
    }

    public void setNewTracks(List<Track> newTracks) {
        this.newTracks = newTracks;
    }
   
    
 
}
