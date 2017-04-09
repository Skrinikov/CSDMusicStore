package com.fractals.converters;


import com.fractals.beans.Album;
import com.fractals.controllers.AlbumJpaController;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named("albumConverter")
@RequestScoped
public class AlbumConverter implements Converter {
 
    @Inject
    AlbumJpaController service;
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {       
                return service.findAlbum(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid album."));
            }     
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Album) object).getId());
        } else {
            return null;
        }
    }
}
