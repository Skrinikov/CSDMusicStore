/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Genre;
import com.fractals.controllers.GenreJpaController;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("theGenres")
@RequestScoped
public class GenreBackingBean {
    
    @Inject
    private GenreJpaController genreJpaController;
    
    public List<Genre> getGenres(){
        return genreJpaController.findGenreEntities();
    }
    
       public boolean isEmpty(){
          return genreJpaController.isEmpty();
      }
}
