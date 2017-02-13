/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    
}
