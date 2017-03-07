/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *  This class will be used to interact with the browse xhtml page and
 *  and read tracks in each genre and display it to the user; 
 * 
 * @author Renuchan
 */
@Named("browseBacking")
@RequestScoped
public class BrowseGenreBacking {
    
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    @Resource
    private UserTransaction userTransaction;
    
    /**
     * This method will get all the genre in the database. 
     * 
     * @return           List of genre
     */
    public List<Genre> getAllGenre()
    {
        //Build Query 
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Genre.class); 
        Root<Genre> genre = cq.from(Genre.class); 
        cq.select(genre); 
        
        //execute the query
        TypedQuery query = entityManager.createQuery(cq);
        return query.getResultList();
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
        //Build query 
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Track.class);
        Root<Track> track = cq.from(Track.class);
        
        cq.where(cb.equal(track.get("genre"), genre));
        
        TypedQuery query = entityManager.createQuery(cq);
        return query.getResultList(); 
    }        
}
