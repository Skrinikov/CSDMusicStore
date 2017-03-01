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
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        //By Default, putting putting a limit of 3
        return getSimilarTracks(track, 3);
    }

     /**
     * Selects tracks based on a genre of a Track
     * 
     * @param track
     * @param count amount of tracks to return
     * @return list of similar tracks
     */
    public List<Track> getSimilarTracks (Track track, int count){
        if (count < 1){
            return new ArrayList<Track>();
        }
        
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<Track> root = query.from(Track.class);
        query.select(root);
        query.where(cb
                .and(
                    cb.equal(root.get("genre"), track.getGenre()),
                    cb.notEqual(root.get("id"), track.getId())
                )
            );
        
        List<Track> tracks = entityManager.createQuery(query).getResultList();
        
        
        
        
        return getRandomTracks(tracks, count);
    }
    
    /**
     * Takes the list of Tracks, shuffles it 
     * and returns the first Tracks based on the limit
     * @param tracks
     * @param limit
     * @return Randomized List of n limit of Tracks
     */
    private List<Track> getRandomTracks(List<Track> tracks, int limit){
        Collections.shuffle(tracks);
        
        if (limit > tracks.size())
            limit = tracks.size();
        
        List<Track> newTracks = new ArrayList<>();
        
        for (int i = 0; i < limit; i++){
            newTracks.add(tracks.get(i));
        }
        
        return newTracks;
    }
}
