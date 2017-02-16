
package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.beans.OrderItem;
import com.fractals.beans.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;


import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
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
        
        User user = new User();
        user.setId(new Integer(1));
        
        String query = 
        "SELECT p FROM OrderItem p JOIN p.order o WHERE o.user = ?1";
                
                
        TypedQuery<OrderItem> q = entityManager.createQuery(query, OrderItem.class);
        q.setParameter(1, user);
        List<OrderItem>items = (List<OrderItem>)q.getResultList();  
        
        //grab the tracks from the orderitems
        
        List<Track> tracks = new ArrayList<>(); 
        for(OrderItem oi : items)
            tracks.add(oi.getTrack());

        return tracks;  
    }
    
    public List<Album> getAlbums()
    {     
        
        User user = new User();
        user.setId(new Integer(1));
        
        String query = 
        "SELECT p FROM OrderItem p JOIN p.order o WHERE o.user = ?1";
                           
        TypedQuery<OrderItem> q = entityManager.createQuery(query, OrderItem.class);
        q.setParameter(1, user);
        List<OrderItem>items = (List<OrderItem>)q.getResultList();  
        
        List<Album> albums = new ArrayList<>(); 
        for(OrderItem oi : items)
            albums.add(oi.getAlbum());
        
        return albums; 
    }
    
    
    
}
