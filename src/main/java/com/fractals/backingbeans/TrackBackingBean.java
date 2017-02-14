/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Track;
import com.fractals.controllers.TrackJpaController;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("theTracks")
@RequestScoped
public class TrackBackingBean {
    @Inject
    private TrackJpaController trackJpaController;
    
    public List<Track> getTracks(){
        return trackJpaController.findTrackEntities();
    }
    
    
       public boolean isEmpty(){
          return trackJpaController.isEmpty();
      }
       
       public void edit(Track t) throws RollbackFailureException, Exception{
           trackJpaController.edit(t);
       }
       
          public Track findTrack(Integer id) {
              return trackJpaController.findTrack(id);
        }
    
}
