/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.BannerAd;
import com.fractals.controllers.BannerAdJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sarah
 */

@Named("theBannerAds")
@SessionScoped
public class BannerAdBackingBean implements Serializable {
    @Inject
    private BannerAdJpaController bannerAdJpaController;
    
    public List<BannerAd> getBannerAds(){
        return bannerAdJpaController.findBannerAdEntities();
    }
    
    public boolean isEmpty(){ return bannerAdJpaController.isEmpty();}
    
    private BannerAd selectedBannerAd;
    public BannerAd getSelectedBannerAd(){ return selectedBannerAd;}
    public void setSelectedBannerAd(BannerAd b){ selectedBannerAd = b;}
   
    private BannerAd createdBannerAd;
    public BannerAd getCreatedBannerAd(){
        if(createdBannerAd == null)
            createdBannerAd = new BannerAd();
        return createdBannerAd;
    }
    public void setCreatedBannerAd(BannerAd b){ createdBannerAd = b;}
     
    public String create() throws Exception {
        bannerAdJpaController.create(createdBannerAd);
        selectedBannerAd = createdBannerAd;
        createdBannerAd = null;
        return "/management/ad/adsViewEdit.xhtml";
    }
}
