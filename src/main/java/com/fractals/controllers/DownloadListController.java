
package com.fractals.controllers;

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
    
    /**
     * http://stackoverflow.com/questions/9391838/how-to-provide-a-file-download-from-a-jsf-backing-bean
     */
    public void downloadSong()
    {
        //ensure that this method is not invoke with an ajax request 
        
        OutputStream output = null;
        byte[] file = getFile();
        if(file != null)
        {
            try {
                FacesContext fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();

                ec.responseReset(); 
                ec.setResponseContentType("application/octet-stream"); 
                ec.setResponseContentLength(file.length); 
                ec.setResponseHeader("Content-Disposition", "attachment; filename=\"song.mp3" + file.length + "\""); 
                output = ec.getResponseOutputStream();
                
                output.write(file);
                output.flush();

                fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
            } catch (IOException ex) {
                Logger.getLogger(DownloadListController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("In bad");
            } finally {
                try {
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(DownloadListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
   
    private byte[] getFile()
    {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        String path = ec.getRealPath("/resources/images/covers/music-note.png");

        byte[] file = null; 
        try {
            file = Files.readAllBytes(new File(path).toPath());
        } catch (IOException ex) {
            Logger.getLogger(DownloadListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return file;      
    }

}
