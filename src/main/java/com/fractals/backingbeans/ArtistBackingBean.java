/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Artist;
import com.fractals.controllers.ArtistJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("theArtists")
@RequestScoped
public class ArtistBackingBean implements Serializable{

    @Inject
    private ArtistJpaController artistJpaController;

    public List<Artist> getArtists() {
        return artistJpaController.findArtistEntities();
    }

    public boolean isEmpty() {
        return artistJpaController.isEmpty();
    }
    
    
    

}
