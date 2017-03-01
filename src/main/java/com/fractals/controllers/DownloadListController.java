
package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.beans.OrderItem;
import com.fractals.beans.User;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;


import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;

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
    
    @Inject
    private LoginController lc; 
    
    
    public DownloadListController()
    {
        super();
    }
   
    
    public List<Track> getTracks()
    {    
        //User user = lc.getCurrentUser();
        
        User user = new User(); 
        user.setId(new Integer(69));
        
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
        //User user = lc.getCurrentUser();
        
        User user = new User();
        user.setId(new Integer(69));
        
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
    
    public String getArtistsName(List<Artist> list)
    {
       String artistPreview = "";
       
       if(list != null)
       {
            int size = list.size();
            
            if(size > 0)
                artistPreview = list.get(0).getName(); 
            if(size > 1)
                artistPreview = artistPreview + "...";  
       }
        
        return artistPreview;            
    }
    
    
    
    
}
