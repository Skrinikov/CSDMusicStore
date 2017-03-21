
package com.fractals.backingbeans;

import com.fractals.controllers.NewsFeedJpaController;
import com.fractals.beans.NewsFeed;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.Serializable;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * This class will be used to interact with page that are in charge of managing 
 * newsFeeds in the database
 * 
 * @author Renuchan
 */
@Named("nfsBackingBean")
@SessionScoped
public class NewsFeedBacking implements Serializable{
    
    private NewsFeed current; 
    
    @Inject
    private NewsFeedJpaController nfc; 
  
    public NewsFeedBacking()
    {}
    
      
    /**
     * This method will be used to obtain the current newsFeed 
     * @return 
     */
    public NewsFeed getCurrent()
    {
        return current; 
    }
    

    /**
     * This method is used ti obtain all the news feeds in the database 
     * @return 
     */
    public List<NewsFeed> getAllNews()
    {
        List<NewsFeed> news = nfc.findNewsFeedEntities();
        
        return news; 
    }
    

    /**
     * This method is used to prepare the page for a create or edit 
     * @param id
     * @return 
     */
    public String prepareChange(Integer id)
    {
        
        if(id == 0)
            this.current = new NewsFeed();
        else
            this.current = nfc.findNewsFeed(id);
        
        return "/management/newsFeed/Edit.xhtml"; 
    }

    /**
     * Creates or Edits a newsFeed
     * @return 
     */
    public String persistChanges()
    {
        try {
        
            if(current.getId() == null || current.getId() == 0)
                nfc.create(current);        
            else
                nfc.edit(current);
        } 
        catch (Exception ex) {
            Logger.getLogger(NewsFeedBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        return "List"; 
    }
    
    
    
    
    //----- Destroy 
    
    /**
     * This method will be used to delete a newsFeed in the database.  
     * @param id        id of the newsFeed to delete
     * @return 
     */
    public String deleteNews(Integer id)
    {   
        
        try {
            nfc.destroy(id);            
        } catch (RollbackFailureException ex) {
            Logger.getLogger(NewsFeedBacking.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(BrowseGenreBacking.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex) {
            Logger.getLogger(NewsFeedBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
}
