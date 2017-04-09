package com.fractals.converters;


import com.fractals.beans.Artist;
import com.fractals.controllers.ArtistJpaController;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Converter for the use of selectOneMenu containing Artists
 * @author Sarah
 */
@Named("artistConverter")
@RequestScoped
public class ArtistConverter implements Converter {
 
    @Inject
    ArtistJpaController service;
    /**
     * Gets the Artist object corresponding to the String
     * @param fc
     * @param uic
     * @param value, the String
     * @return 
     */
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {       
                return service.findArtist(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }     
        } else {
            return null;
        }
    }
    
    /**
     * Gets the String value of an Artist, here its id
     * @param fc
     * @param uic
     * @param object, the Artist
     * @return 
     */
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Artist) object).getId());
        } else {
            return null;
        }
    }
}
