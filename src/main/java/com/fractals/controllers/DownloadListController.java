package com.fractals.controllers;

import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.User;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
/**
 *
 * This controller will be used to retrieve all tracks and albums the users 
 * purchased, in order to download the mp3 file
 * 
 * @author Danieil Skrinikov
 * @version 1.2.0
 * 
 */
@Named("DLController")
@RequestScoped
public class DownloadListController implements Serializable {  
  
    @Inject 
    private OrderItemJpaController oijc; 
    
    /**
     * Fetches all the tracks that a given user bought. Orders them by descending
     * order date.
     * 
     * @param user user 
     * @return 
     */
    public List<Track> getClientTracks(User user){
        if(user == null){
            return null;
        }
        List<Track> tracks = new ArrayList<>();
        
        List<OrderItem> oi = oijc.getUsersOrders(user);
        
        for(OrderItem item : oi){
            if(item.getAlbum() != null){
              tracks.addAll(item.getAlbum().getTracks());
            } 
            else if(item.getTrack() != null){
                tracks.add(item.getTrack());
            }
        }

        return tracks;
    }
    
    /**
     * Returns the file as a streamed content for downloading. Since this is not
     * a fully functional project it always returns the same file. To change this
     * in the future, this method should take a Track object as a parameter and
     * fetch the appropriate song from the database.
     * 
     * @author Renuchan
     * @return file to download.
     */
    public StreamedContent downloadSong() { 

        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/mp3/sample.mp3");
        StreamedContent file = new DefaultStreamedContent(stream, "audio/mpeg", "sample.mp3");
        return file; 
    }

}
