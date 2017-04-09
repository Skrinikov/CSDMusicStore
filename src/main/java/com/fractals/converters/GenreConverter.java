package com.fractals.converters;


import com.fractals.beans.Genre;
import com.fractals.controllers.GenreJpaController;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Converter for the use of selectOneMenu containing Genres
 * @author Sarah
 */
@Named("genreConverter")
@RequestScoped
public class GenreConverter implements Converter {
 
    @Inject
    GenreJpaController service;
    
    /**
     * Gets the Genre object corresponding to the String
     * If the object doesn't exist, it is created and an info message 
     * is displayed
     * @param fc
     * @param uic
     * @param value, the String
     * @return 
     */
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       if (value != null && value.trim().length() > 0) {
            try {       
                return service.findGenre(Integer.parseInt(value));
            } catch (NumberFormatException e) {            
               Genre g = new Genre();
               g.setName(value);
                try {
                    service.create(g);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The " + value + " genre didn't exist so it has been created ") );
                    return service.findGenre(service.findGenreEntities().size());  
                } catch (Exception e2) {}
            }     
        }  
            return null;        
    }
    /**
     * Gets the String value of a Genre, here its id
     * @param fc
     * @param uic
     * @param object, the Genre
     * @return 
     */
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Genre) object).getId());
        } else {
            return null;
        }
    }
}
