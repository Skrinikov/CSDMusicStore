
package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;


import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * This controller will be used to retrieve all tracks and albums the users 
 * purchased, in order to download the mp3 file
 * 
 * @author Renuchan
 * 
 */

@Named("DLController")
@SessionScoped
public class DownloadListController implements Serializable
{
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    @Resource
    private UserTransaction userTransaction;
    
    
    public DownloadListController()
    {
        super();
    }
   
    
    public List<Track> getTracks()
    {
        //plz write the proper query 
        
        
        //String query = "select track_id"
        
        //String query = "select t from OrderItem o \n" +
         //               "Join o.orderId o where o.trackId = :userId";
        

        // Object oriented criteria builder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Track> cq = cb.createQuery(Track.class);
        Root<Track> t = cq.from(Track.class);
        cq.select(t);
               
        TypedQuery<Track> query =  entityManager.createNamedQuery("Track.findAll", Track.class);
              
        List<Track> tracks = query.getResultList();
        
        return tracks;  
    }
    
    public List<Album> getAlbums()
    {
        
        //plz write the proper query 
        
        // Object oriented criteria builder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);
        Root<Album> a = cq.from(Album.class);
        cq.select(a);
               
        TypedQuery<Album> query =  entityManager.createNamedQuery("Album.findAll", Album.class);
              
        List<Album> albums = query.getResultList();
        
        return albums; 
    }
    
    
    
}
