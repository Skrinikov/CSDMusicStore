/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Genre;
import com.fractals.controllers.GenreJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("theGenres")
@SessionScoped
public class GenreBackingBean implements Serializable {
    
    @Inject
    private GenreJpaController genreJpaController;
    
    public List<Genre> getGenres(){
        return genreJpaController.findGenreEntities();
    }
    
    public boolean isEmpty(){
       return genreJpaController.isEmpty();
    }
       
       
    private Genre createdGenre;
    public Genre getCreatedGenre(){
        if(createdGenre == null)
            createdGenre = new Genre();
        return createdGenre;
    }
    public void setCreatedGenre(Genre g){createdGenre = g;}
    
    private Genre selectedGenre;
    public Genre getSelectedGenre(){ return selectedGenre;}
    public void setSelectedGenre(Genre g){selectedGenre = g;}
    
    private boolean editable = false;
    public boolean getEditable() {return editable;}
    public void setEditable(boolean b) {editable = b;}
    public void makeEditable(){setEditable(true);};
    public void makeUneditable(){setEditable(false);}
    
    
    public String create() throws Exception {
        genreJpaController.create(createdGenre);
        selectedGenre = createdGenre;
        createdGenre = null;
        return "/management/genre/genresViewEdit.xhtml";
    }
       
    public String edit()throws Exception {
        genreJpaController.edit(selectedGenre);
        return "/management/genre/genresList.xhtml";
    }

    public void delete() throws Exception {
        genreJpaController.destroy(selectedGenre.getId());
    }
}
