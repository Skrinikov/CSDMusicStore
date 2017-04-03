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
 * @author MOUFFOK Sarah
 */
@Named("theBannerAds")
@SessionScoped
public class BannerAdBackingBean implements Serializable {

    @Inject
    private BannerAdJpaController bannerAdJpaController;
    private BannerAd selectedBannerAd, createdBannerAd;

    public List<BannerAd> getBannerAds() {
        return bannerAdJpaController.findBannerAdEntities();
    }

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

    public void create() throws Exception {
        bannerAdJpaController.create(createdBannerAd);
        selectedBannerAd = createdBannerAd;
        createdBannerAd = null;
    }

    public void edit() throws Exception{
        bannerAdJpaController.edit(selectedBannerAd);
    }
}
