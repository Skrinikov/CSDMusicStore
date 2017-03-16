package com.fractals.backingbeans;

import com.fractals.beans.Track;
import com.fractals.controllers.DownloadListController;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;

/**
 * This bean contains all the necessary logic to display the downloadable files 
 * for a given user.
 * 
 * 
 * @author Danieil Skrinikov
 * @version 1.0.01
 * 
 */
@Named
@SessionScoped
public class DownloadsBacking implements Serializable{
    
    private static transient final java.util.logging.Logger log = java.util.logging.Logger.getLogger(DownloadsBacking.class.getName());
    
    @Inject
    private LoginBacking login;
    
    @Inject
    private DownloadListController downloadController;
    
    private List<Track> downloadableTracks;
    
    /**
     * Initializes all components required to display the page and makes sure the
     * person accessing this page is authenticated.
     */
    public void init(){
        if(!login.isLoggedIn()){
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String outcome = "login.xhtml";
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
        }
        
        downloadableTracks = downloadController.getClientTracks(login.getCurrentUser());
    }

    
    public StreamedContent getDownloadableFile(){
        return downloadController.downloadSong();
    }
    
    /*
     * Getters and setters.
     */
    
    public List<Track> getDownloadableTracks() {
        return downloadableTracks;
    }

    public void setDownloadableTracks(List<Track> downloadableTracks) {
        this.downloadableTracks = downloadableTracks;
    }
    
}
