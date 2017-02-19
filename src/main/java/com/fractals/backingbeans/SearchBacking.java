package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.controllers.SearchJPAController;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Responsible for backing search choices page.
 *
 * @author Aline Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
@Named("search")
@SessionScoped
public class SearchBacking  implements Serializable  {
    private List<String> options;
    private SearchJPAController jpa;
    private String choice;
    private String key;
    private boolean dateSelected;
    private Date dateStart;
    private Date dateEnd;
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    /**
     * Returns starting date for the query.
     * @return starting date for the query.
     */
    public Date getDateStart() {
        return dateStart;
    }
    
    /**
     * Returns ending date for the query.
     * @return ending date for the query.
     */
    public Date getDateEnd() {
        return dateEnd;
    }
    
    /**
     * Returns all available search options.
     * @return all available search options.
     */
    public List<String> getOptions() {
        return options;
    }
    
    /**
     * Returns a boolean representing if date option was selected.
     * @return a boolean representing if date option was selected.
     */
    public boolean getDateSelected() {
        return dateSelected;
    }
    
    /**
     * Returns the current search choice of the user.
     * @return the current search choice of the user.
     */
    public String getChoice() {
        return choice;
    }
    
    /**
     * Sets the current search choice of the user.
     * @param choice The current search choice of the user.
     */
    public void setChoice(String choice) {
        this.choice = choice;
    }
    
    /**
     * Returns the user-entered search key.
     * @return the user-entered search key.
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Sets the user-entered search key.
     * @param key The user-entered search key.
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Queries for albums based on user-defined search keys and he chosen option.
     * @return 
     */
    public String search() {
        LocalDateTime from = LocalDateTime.ofInstant(dateStart.toInstant(), ZoneId.systemDefault());
        LocalDateTime to = LocalDateTime.ofInstant(dateEnd.toInstant(), ZoneId.systemDefault());
        List<Album> albums = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();
        if(choice.equals(options.get(0)))
            albums = jpa.searchByAlbumTitle(key);
        else if(choice.equals(options.get(1)))
            tracks = jpa.searchByTrackName(key);
        else if(choice.equals(options.get(2))) {
            albums = jpa.searchByDateAlbums(from, to);
            tracks = jpa.searchByDateTracks(from, to);
        }
        else if(choice.equals(options.get(3))) {
            albums = jpa.searchByArtistNameAlbums(key);
            tracks = jpa.searchByArtistNameTracks(key);
        }
        System.out.println("ALBUMS" + Arrays.toString(albums.toArray(new Album[]{})) +  "TRACKS" + Arrays.toString(tracks.toArray(new Track[]{})));
        return null;
    }
    
    /**
     * An event that is executes when the user selects an option on the menu.
     */
    public void onItemSelect() {
        dateSelected = choice.equals(options.get(2));
    }

    
    /**
     * Initializes fields.
     */
    @PostConstruct
    public void init() {
       options = new ArrayList<>();
       options.add("Search by album title");
       options.add("Search by track name");
       options.add("Search by created date");
       options.add("Search by artist name");
       jpa = new SearchJPAController(entityManager);
       dateStart = Date.from(LocalDate.now().minusWeeks(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
       dateEnd = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
       dateSelected = choice != null ? options.get(2).equals(choice) : false;;
    }
}
