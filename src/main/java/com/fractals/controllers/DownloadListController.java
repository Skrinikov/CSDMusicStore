
package com.fractals.controllers;

import com.fractals.backingbeans.LoginBacking;
import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import com.fractals.beans.OrderItem;
import com.fractals.beans.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;


import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;

import java.io.OutputStream; 
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
    private LoginBacking lc; 
    
    private List<Track> trackList; 
    private List<Album> albumList; 
    
    public DownloadListController()
    {
        super();
    }

    public List<Track> getTrackList() {     
        return trackList;     
    }

    public List<Album> getAlbumList() {
        getAlbums();
        return albumList;
    }

    
    public List<Track> getTracks()
    {    
        User user = lc.getCurrentUser();
        
        String query = 
        "SELECT p FROM OrderItem p JOIN p.order o WHERE o.user = ?1";
                              
        TypedQuery<OrderItem> q = entityManager.createQuery(query, OrderItem.class);
        q.setParameter(1, user);
        List<OrderItem>items = (List<OrderItem>)q.getResultList();  
        
        //grab the tracks from the orderitems
        
        List<Track> tracks = new ArrayList<>(); 
        for(OrderItem oi : items)
            tracks.add(oi.getTrack());

        this.trackList = tracks;
        return tracks;
    }
    
    public List<Album> getAlbums()
    {     
        User user = lc.getCurrentUser();
               
        String query = 
        "SELECT p FROM OrderItem p JOIN p.order o WHERE o.user = ?1";
                           
        TypedQuery<OrderItem> q = entityManager.createQuery(query, OrderItem.class);
        q.setParameter(1, user);
        List<OrderItem>items = (List<OrderItem>)q.getResultList();  
        
        List<Album> albums = new ArrayList<>(); 
        for(OrderItem oi : items)
            albums.add(oi.getAlbum());
        
        this.albumList = albums;
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
  
    public StreamedContent downloadSong() { 

        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/mp3/sample.mp3");
        StreamedContent file = new DefaultStreamedContent(stream, "audio/mpeg", "sample.mp3");
        return file; 
    }
    
    public String getArtistName(List<Artist> list)
    {
        if(list != null && list.size() > 0)
            return list.get(0).getName();
        else 
            return ""; 
    }
    
    public int getCount(List<Object> list)
    {            
        if(list == null)
            return 0; 
        else
            return list.size(); 
    }
}
