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
 *  Class containing the methods and variables for managing Genres
 * @author MOUFFOK Sarah
 */
@Named("theGenres")
@SessionScoped
public class GenreBackingBean implements Serializable {

    @Inject
    private GenreJpaController genreJpaController;
    private Genre selectedGenre, createdGenre;
    /**
     * Whether the manager is viewing or editing the selectedGenre
     */
    private boolean editable = false;
    /**
     * @return a list of all the existing genres
     */
    public List<Genre> getGenres() {
        return genreJpaController.findGenreEntities();
    }
    /**
     * @return whether there are any genres in the database
     */
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
    /**
     * Creates the createdGenre
     * @throws Exception 
     */
    public void create() throws Exception {
        genreJpaController.create(createdGenre);
        selectedGenre = createdGenre;
        createdGenre = null;
    }
    /**
     * Edits the selectedGenre 
     * @throws Exception 
     */
    public void edit() throws Exception {
        makeUneditable();
        genreJpaController.edit(selectedGenre);
    }
    /**
     * @return the number of pages of a 10 row datatable containing 
     *all the genres
     */
    public int getPageCount() {
        return genreJpaController.getGenreCount() / 10;
    }
    /**
     * Suggests genres that start with the String s for autocomplete tags
     * @param s
     * @return a list of Genres that start with the String s
     */
    public List<Genre> suggest(String s) {
        ArrayList<Genre> similar = new ArrayList<>();
        for (Genre g : genreJpaController.findGenreEntities()) 
            if (g.getName().toLowerCase().startsWith(s.toLowerCase())) 
                similar.add(g);   
        return similar;
    }
}
