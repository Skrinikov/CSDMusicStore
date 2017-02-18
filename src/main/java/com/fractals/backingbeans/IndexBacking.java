package com.fractals.backingbeans;

import com.fractals.controllers.RssFeedController;
import com.fractals.rss.FeedMessage;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This bean is used by the index page to fetch important data from various controllers.
 * 
 * @author Danieil Skrinikov
 * @version 1.0.0
 * @since 2017-02-17
 */
@Named("indexBacking")
@RequestScoped
public class IndexBacking {
    
    // Controllers
    @Inject
    private RssFeedController rss;
    
    // Class Variables
    private List<FeedMessage> rssMsgs;
    
    /**
     * Generates all components which are needed to display the page.
     */
    @PostConstruct
    public void init(){
        rssMsgs = rss.getVisibleFeed();
        System.out.println(rssMsgs.size());
    }
    
    /* Getters and Setters */

    public List<FeedMessage> getRssMsgs() {
        return rssMsgs;
    }

    public void setRssMsgs(List<FeedMessage> rssMsgs) {
        this.rssMsgs = rssMsgs;
    }
 
}
