package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;

/**
 * Implements searching by album title, tracks name, artist name, and 
 * between specific dates for the index page.
 *
 * @author Aline Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
@Named
@RequestScoped
public class SearchJPAController implements Serializable {

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    /**
     * Searches for albums by the album title.
     * @param title The album title.
     * @return the list of albums corresponding to this condition.
     */
    public List<Album> searchByAlbumTitle(String title) {
        if(title == null)
            throw new NullPointerException();
              
        TypedQuery<Album> q = entityManager.createQuery("select a from Album a where a.title like ?1", Album.class);
        q.setParameter(1, "%"+title+"%");
        return (List<Album>)q.getResultList();  
    }
    
    /**
     * Searches for tracks by the track name.
     * @param title The track name.
     * @return the list of tracks corresponding to this condition.
     */
    public List<Track> searchByTrackName(String title) {
        if(title == null)
            throw new NullPointerException();
            
        TypedQuery<Track> q = entityManager.createQuery("select t from Track t where t.title like ?1", Track.class);
        q.setParameter(1, "%"+title+"%");
        return (List<Track>)q.getResultList();      
    }
    
    /**
     * Searches for albums by the artist name.
     * @param name The artist name.
     * @return the list of albums corresponding to this condition.
     */
    public List<Album> searchByArtistNameAlbums(String name) {
        if(name == null)
            throw new NullPointerException();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Album> cqA = cb.createQuery(Album.class);      
        Root<Album> album = cqA.from(Album.class);       
        Join artistJ = album.join("artist");
        cqA.where(cb.like(artistJ.get("name"), "%"+name+"%"));
        TypedQuery<Album> tqA = entityManager.createQuery(cqA);      
        List<Album> albums = (List<Album>)tqA.getResultList(); 
        
        return albums;
    }
    
    /**
     * Searches for tracks by the artist name
     * @param name The artist name.
     * @return the list of tracks corresponding to this condition.
     */
    public List<Track> searchByArtistNameTracks(String name) {
        if(name == null)
            throw new NullPointerException();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Track> cq = cb.createQuery(Track.class);
        Root<Artist> artist = cq.from(Artist.class);
        cq.where(cb.like(artist.get("name"), "%"+name+"%"));
        Join<Artist, Track> artists = artist.join("tracks");
        CriteriaQuery<Track> cqT = cq.select(artists);
        TypedQuery<Track> query = entityManager.createQuery(cqT);
        List<Track> tracks = (List<Track>)query.getResultList();
        
        return tracks;
    }
      
    /**
     * Searches for albums that were created between specified dates.
     * @param from The date from which the created albums are searched for.
     * @param to The date to which the created albums are searched for.
     * @return the list of albums corresponding to this condition.
     */
    public List<Album> searchByDateAlbums(LocalDateTime from, LocalDateTime to) {
        if(from == null || to == null )
            throw new NullPointerException();
        if(from.isAfter(to))
            throw new DateTimeException("From date is after to date");
                
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();   
        
        CriteriaQuery<Album> cqA = cb.createQuery(Album.class);      
        Root<Album> album = cqA.from(Album.class);       
        cqA.where(cb.between(album.<LocalDateTime>get("createdAt"), from, to));
        TypedQuery<Album> tqA = entityManager.createQuery(cqA);      
        List<Album> albums = (List<Album>)tqA.getResultList(); 
        
        return albums;
    }
    
    /**
     * Searches for tracks that were created between specified dates.
     * @param from The date from which the created tracks are searched for.
     * @param to The date to which the created tracks are searched for.
     * @return the list of tracks corresponding to this condition.
     */
    public List<Track> searchByDateTracks(LocalDateTime from, LocalDateTime to) {
        if(from == null || to == null )
            throw new NullPointerException();
        if(from.isAfter(to))
            throw new DateTimeException("From date is after to date");
                
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();   
              
        CriteriaQuery<Track> cqT = cb.createQuery(Track.class);      
        Root<Track> track = cqT.from(Track.class);       
        cqT.where(cb.between(track.<LocalDateTime>get("createdAt"), from, to));
        TypedQuery<Track> tqT = entityManager.createQuery(cqT);      
        List<Track> tracks = (List<Track>)tqT.getResultList(); 
        
        return tracks;
    }
}
