package com.fractals.backingbeans;

import com.fractals.beans.BannerAd;
import com.fractals.controllers.BannerAdJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class containing the methods and variables for managing Banner Ads
 * @author MOUFFOK Sarah
 */
@Named("theBannerAds")
@SessionScoped
public class BannerAdBackingBean implements Serializable {

    @Inject
    private BannerAdJpaController bannerAdJpaController;
    private BannerAd selectedBannerAd, createdBannerAd;
    /**
     * @return all the existing banner ads
     */
    public List<BannerAd> getBannerAds() {
        return bannerAdJpaController.findBannerAdEntities();
    }
    
    /**
     * @return whether there are any banner ads in the database
    */
    public boolean isEmpty() {
        return bannerAdJpaController.isEmpty();
    }

    public BannerAd getSelectedBannerAd() {
        return selectedBannerAd;
    }

    public void setSelectedBannerAd(BannerAd b) {
        selectedBannerAd = b;
    }

    public BannerAd getCreatedBannerAd() {
        if (createdBannerAd == null) 
            createdBannerAd = new BannerAd();
        return createdBannerAd;
    }

    public void setCreatedBannerAd(BannerAd b) {
        createdBannerAd = b;
    }
    /**
     * Created the createdBannerAd
     * @throws Exception 
     */
    public void create() throws Exception {
        bannerAdJpaController.create(createdBannerAd);
        selectedBannerAd = createdBannerAd;
        createdBannerAd = null;
    }
    /**
     * Edit the selectedBannerAd
     * @throws Exception 
     */
    public void edit() throws Exception{
        bannerAdJpaController.edit(selectedBannerAd);
    }
}
