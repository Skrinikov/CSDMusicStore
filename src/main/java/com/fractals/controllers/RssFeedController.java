
package com.fractals.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.UserTransaction;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import com.fractals.beans.User;
import com.fractals.beans.NewsFeed;
import com.fractals.rss.*; 
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


/**
 *  This controller will be used to communicate with the jsf file that utilizes 
 *  the rss feed
 * 
 * @author Renuchan
 */
@Named("rssController")
@RequestScoped
public class RssFeedController {
    
    @Resource
    private UserTransaction utx; 
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em; 
    
    
    
    public RssFeedController()
    {
        super(); 
    }
    
    /**
     * This method will be used to obtain the beans containing the rss feeds info
     * 
     * @return 
     */
    public List<FeedMessage> getVisibleFeed ()
    {
        List<String> links = getLinks();
        String[] array = new String[links.size()]; 
        RSSFeedParser rfp = new RSSFeedParser(); 
        return rfp.readFeed(links.toArray(array));
    }
    
    /*
     * Gets the links assoicated to a particular news Feed link. 
     * @return a list of links for that news feed 
     */
    private List<String> getLinks()
    {
        String query = "select nf from NewsFeed nf where nf.visible = TRUE"; 
        
        TypedQuery<NewsFeed> q = em.createQuery(query, NewsFeed.class); 
        List<NewsFeed> nfs = q.getResultList();
         
        List<String> links = new ArrayList<>();
        
        for(NewsFeed nf : nfs)
            links.add(nf.getLink());
        
        return links; 
        
    }

    /**
     * Gets the image link of a feed Message
     * @param fm
     * @return img path of the feedMessage
     */
    public String getImgLink(FeedMessage fm)
    {
        String content = fm.getDescription();
        
        int start = content.indexOf("'") + 1;
        int end = content.indexOf("'", start);
        
        return content.substring(start, end); 
    }
    
    /**
     * Extracts the description information within the html tags. 
     * @param fm
     * @return the content without any tags.
     */
    public String getDescription(FeedMessage fm)
    {
        String content = fm.getDescription();
        int start = content.indexOf("<p>") + 3;
        int end = content.indexOf("</p>"); 
        
        return content.substring(start, end);
    }
    
}
