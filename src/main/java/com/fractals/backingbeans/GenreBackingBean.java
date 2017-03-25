/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Genre;
import com.fractals.controllers.GenreJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named("theGenres")
@SessionScoped
public class GenreBackingBean implements Serializable {

    @Inject
    private GenreJpaController genreJpaController;
    private Genre selectedGenre, createdGenre;
    private boolean editable = false;

    public List<Genre> getGenres() {
        return genreJpaController.findGenreEntities();
    }

    public boolean isEmpty() {
        return genreJpaController.isEmpty();
    }

    public Genre getCreatedGenre() {
        if (createdGenre == null) {
            createdGenre = new Genre();
        }
        return createdGenre;
    }

    public void setCreatedGenre(Genre g) {
        createdGenre = g;
    }

    public Genre getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(Genre g) {
        selectedGenre = g;
        makeUneditable();
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean b) {
        editable = b;
    }

    public void makeEditable() {
        setEditable(true);
    }

    public void makeUneditable() {
        setEditable(false);
    }

    public void create() throws Exception {
        genreJpaController.create(createdGenre);
        selectedGenre = createdGenre;
        createdGenre = null;
    }

    public void edit() throws Exception {
        makeUneditable();
        genreJpaController.edit(selectedGenre);
    }

    public void delete() throws Exception {
        genreJpaController.destroy(selectedGenre.getId());
    }

    public int getPageCount() {
        return genreJpaController.getGenreCount() / 10;
    }

    public List<Genre> suggest(String s) {
        ArrayList<Genre> similar = new ArrayList<>();
        for (Genre g : genreJpaController.findGenreEntities()) 
            if (g.getName().toLowerCase().startsWith(s.toLowerCase())) 
                similar.add(g);   
        return similar;
    }
}
