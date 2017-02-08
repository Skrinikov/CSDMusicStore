package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
//import com.fractals.beans.Artist_;
import com.fractals.beans.Track;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;

/**
 *
 * @author lynn
 */
@Named
@RequestScoped
public class SearchJPAController implements Serializable {
    
    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    
    public List<Album> searchByAlbumTitle(String title) {
        if(title == null)
            throw new NullPointerException();
        
        List<Album> items = new ArrayList<>();      
        TypedQuery<Album> q = entityManager.createQuery("select a from Album a where a.title like ?1", Album.class);
        q.setParameter(1, "%"+title+"%");
        items = (List<Album>)q.getResultList();  
        return items;
    }
    
    public List<Track> searchByTrackName(String title) {
        if(title == null)
            throw new NullPointerException();
        
        List<Track> items = new ArrayList<>();      
        TypedQuery<Track> q = entityManager.createQuery("select t from Track t where t.title like ?1", Track.class);
        q.setParameter(1, "%"+title+"%");
        items = (List<Track>)q.getResultList();      
        return items;
    }
    
    public Object[] searchByArtistName(String name) {
        if(name == null)
            throw new NullPointerException();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Album> cqA = cb.createQuery(Album.class);      
        Root<Album> album = cqA.from(Album.class);       
        Join artistJ = album.join("Artist");
        cqA.where(cb.like(artistJ.get("name"), "%"+name+"%"));
        TypedQuery<Album> tqA = entityManager.createQuery(cqA);      
        List<Album> albums = (List<Album>)tqA.getResultList(); 
        
        CriteriaQuery<Track> cq = cb.createQuery(Track.class);
        Root<Artist> artist = cq.from(Artist.class);
        cq.where(cb.like(artist.get("name"), "%"+name+"%"));
        Join<Artist, Track> artists = artist.join("tracks");
        CriteriaQuery<Track> cqT = cq.select(artists);
        TypedQuery<Track> query = entityManager.createQuery(cqT);
        List<Track> tracks = (List<Track>)query.getResultList();
        
        return new Object[]{albums, tracks};
    }
        
    public Object[] searchByDate(LocalDateTime from, LocalDateTime to) {
        if(from == null || to == null )
            throw new NullPointerException();
        if(from.isAfter(to))
            throw new DateTimeException("From date is after to date");
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();   
        
        CriteriaQuery<Album> cqA = cb.createQuery(Album.class);      
        Root<Album> album = cqA.from(Album.class);       
        cqA.where(cb.between(album.<Date>get("createdAt"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), 
                                                           Date.from(to.atZone(ZoneId.systemDefault()).toInstant())));
        TypedQuery<Album> tqA = entityManager.createQuery(cqA);      
        List<Album> albums = (List<Album>)tqA.getResultList(); 
              
        CriteriaQuery<Track> cqT = cb.createQuery(Track.class);      
        Root<Track> track = cqT.from(Track.class);       
        cqA.where(cb.between(track.<Date>get("createdAt"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), 
                                                           Date.from(to.atZone(ZoneId.systemDefault()).toInstant())));
        TypedQuery<Track> tqT = entityManager.createQuery(cqT);      
        List<Track> tracks = (List<Track>)tqT.getResultList(); 
        
        return new Object[]{albums, tracks};
    }
}
