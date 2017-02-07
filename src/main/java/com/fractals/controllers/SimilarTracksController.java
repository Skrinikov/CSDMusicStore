/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.beans.Genre;
import com.fractals.beans.Track;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Thai-Vu Nguyen
 */
@Named("similarTracks")
@RequestScoped
public class SimilarTracksController implements Serializable {
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    public SimilarTracksController(){}
    
    /**
     * Gets a list of similar Tracks to a track
     * 
     * @param track Track
     * @return List of Tracks similar to the passed track
     */
    public List<Track> getSimilarTracks(Track track){
        
        List<Track> tracks = new ArrayList<>();
        Genre genre = track.getGenreId();
        TypedQuery<Track> query = entityManager.createQuery("select t from Track a where t.genre_id like %?1%'", Track.class);
        query.setParameter(1, genre.getId());
        tracks = (List<Track>)query.getResultList();
        
        //Taking out the original track from the result
        tracks.remove(track);
        return tracks;
    }

    
}
