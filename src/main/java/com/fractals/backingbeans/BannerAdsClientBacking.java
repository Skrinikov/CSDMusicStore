/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;
import com.fractals.beans.BannerAd;
import com.fractals.controllers.BannerAdJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *  This class will be used to interact with xhtml pages that display 
 *  ads. 
 * 
 * @author Renuchan 
 */

@Named("bannerAdClient")
@RequestScoped
public class BannerAdsClientBacking {
    
    @Inject 
    private BannerAdJpaController bjc; 
    
    
    /**
     * This method will return 2 visible ads in the database and hand it 
     * to the xhtml page who requested the data.
     * @return 
     */
    public List<BannerAd> getVisibleAds()
    {
        List<BannerAd> visibleAds = bjc.getVisible();
        int adsWantedPerPage = 2; 
        List<BannerAd> ads = new ArrayList<>();
        
        for(int i = 0; i < adsWantedPerPage; i++)
            ads.add(selectRandom(visibleAds));
        
        return ads;                    
    }
    
    /*
        Will select and remove a random ad in the list provided 
    */
    private BannerAd selectRandom(List<BannerAd> list)
    {      
        int selected = (int)(Math.random() * list.size());
        return list.remove(selected);
    }

}
