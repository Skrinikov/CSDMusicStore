/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.controllers.AlbumJpaController;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("theAlbums")
@RequestScoped
public class AlbumBackingBean {
    
    @Inject
    private AlbumJpaController albumJpaController;
    
      public List<Album> getAlbums() {
        return albumJpaController.findAlbumEntities();
    }
      
      public boolean isEmpty(){
          return albumJpaController.isEmpty();
      }
}
