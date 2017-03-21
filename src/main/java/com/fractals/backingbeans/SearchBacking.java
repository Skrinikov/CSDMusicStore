package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.controllers.SearchJPAController;
import com.fractals.utilities.BundleLocaleResolution;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Responsible for backing search pages.
 *
 * @author Aline Shulzhenko
 * @version 20/03/2017
 * @since 1.8
 */
@Named("search")
@SessionScoped
public class SearchBacking implements Serializable {
    private List<Album> albums;
    private List<Track> tracks;
    
    @Inject
    private SearchJPAController jpa;
    
    private String choice;
    private String key;
    private String keyQuery;
    private boolean dateSelected;
    private Date dateStart;
    private Date dateEnd;
    private static transient final java.util.logging.Logger log = java.util.logging.Logger.getLogger("SearchBacking.class");
    private ResourceBundle bundle;
    
    /**
     * Returns the cover file of the required album.
     * @param album The album for which the cover is found.
     * @return the cover file of the required album.
     */
    public String getAlbumCover(Album album) {
        List<Track> items = album.getTracks();
        return items.isEmpty() ? null : items.get(0).getCoverFile();
    }
    
    /**
     * Returns true if there is no data available to display.
     * @return true if there is no data available to display; false otherwise.
     */
    public boolean noData() {
        return (albums == null || albums.isEmpty()) && 
               (tracks == null || tracks.isEmpty());
    }
    
    /**
     * Verifies if the albums list is empty.
     * @return true if the list is empty; false otherwise.
     */
    public boolean isAlbumsEmpty() {
        return albums == null || albums.isEmpty();
    }
    
    /**
     * Verifies if the tracks list is empty.
     * @return true if the list is empty; false otherwise.
     */
    public boolean isTracksEmpty() {
        return tracks == null || tracks.isEmpty();
    }
    
    /**
     * Returns the list of queried albums.
     * @return the list of queried albums.
     */
    public List<Album> getAlbums() {
        return albums;
    }
    
    /**
     * Sets the list of queried albums.
     * @param albums The list of queried albums.
     */
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
    
    /**
     * Returns the list of queried tracks.
     * @return  the list of queried tracks.
     */
    public List<Track> getTracks() {
        return tracks;
    }
    
    /**
     * Returns the list of queried tracks.
     * @param tracks The list of queried tracks.
     */
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
    
    /**
     * Returns starting date for the query.
     * @return starting date for the query.
     */
    public Date getDateStart() {
        return dateStart;
    }
    
    /**
     * Returns starting date for the query.
     * @param dateStart starting date for the query.
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    
    /**
     * Returns ending date for the query.
     * @return ending date for the query.
     */
    public Date getDateEnd() {
        return dateEnd;
    }
    
    /**
     * Returns ending date for the query.
     * @param dateEnd ending date for the query.
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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
     * Sets the search key from URL.
     * @return The search key from URL.
     */
    public String getKeyQuery() {
        return keyQuery;
    }
    
    /**
     * Sets the search key from URL.
     * @param keyQuery The search key from URL.
     */
    public void setKeyQuery(String keyQuery) {
        this.keyQuery = keyQuery;
    }
    
    /**
     * Queries for albums based on user-defined search keys and the chosen option.
     * @return the album or a track page if one item is found; null to stay
     *         on the same page if multiple items are found.
     */
    public String search() {
        albums = new ArrayList<>();
        tracks = new ArrayList<>();    
        if(key != null) {
            LocalDateTime from = LocalDateTime.ofInstant(dateStart.toInstant(), ZoneId.systemDefault());
            LocalDateTime to = LocalDateTime.ofInstant(dateEnd.toInstant(), ZoneId.systemDefault());
            
            if(from.isAfter(to)) {
                FacesMessage message = new FacesMessage(bundle.getString("date_seq_error"));
                FacesContext.getCurrentInstance().addMessage("searchForm", message);
            }
            else {
                if(choice.equals(bundle.getString("search_album")))
                    albums = jpa.searchByAlbumTitle(key);
                else if(choice.equals(bundle.getString("search_track")))
                    tracks = jpa.searchByTrackName(key);
                else if(choice.equals(bundle.getString("search_date"))) {
                    albums = jpa.searchByDateAlbums(from, to);
                    tracks = jpa.searchByDateTracks(from, to);
                }
                else if(choice.equals(bundle.getString("search_artist"))) {
                    albums = jpa.searchByArtistNameAlbums(key);
                    tracks = jpa.searchByArtistNameTracks(key);
                }
            }
        }
        if(tracks.size() == 1 && albums.isEmpty())
            return "Track.xhtml?faces-redirect=true&id="+tracks.get(0).getId();
        else if(albums.size() == 1 && tracks.isEmpty())
            return "Album.xhtml?faces-redirect=true&id="+albums.get(0).getId();
        return null;
    }
    
    /**
     * An event that is executes when the user selects an option on the menu.
     */
    public void onItemSelect() {
        albums = new ArrayList<>();
        tracks = new ArrayList<>();
        dateStart = Date.from(LocalDate.now().minusWeeks(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateEnd = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        key = "";
        keyQuery = "";
        dateSelected = choice.equals(bundle.getString("search_date"));
    }

    
    /**
     * Initializes fields.
     */
    @PostConstruct
    public void init() {      
       bundle = new BundleLocaleResolution().returnBundleWithCurrentLocale();
       keyQuery = "";
       choice = "";     
       dateStart = Date.from(LocalDate.now().minusWeeks(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
       dateEnd = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
       dateSelected = choice != null ? bundle.getString("search_date").equals(choice) : false; 
    }
    
    /**
     * Updates the bundle if the locale is changed.
     * Queries the database for tracks if the query string was supplied.
     */
    public void onload() {
        bundle = new BundleLocaleResolution().returnBundleWithCurrentLocale();
        if(keyQuery != null && !keyQuery.isEmpty()) {
            albums = jpa.searchByAlbumTitle(keyQuery);
            tracks = jpa.searchByTrackName(keyQuery);
        }
    }
}
