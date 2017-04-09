/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Converter for the use of selectOneMenu containing Albums
 * @author MOUFFOK Sarah
 */
@Named("albumConverter")
@RequestScoped
public class AlbumConverter implements Converter {
 
    @Inject
    AlbumJpaController service;
    
    /**
     * Gets the Album object corresponding the String value
     * @param fc
     * @param uic
     * @param value, the String
     * @return 
     */
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
    /**
     * Gets the String value of an Album, here its id
     * @param fc
     * @param uic
     * @param object, the Album
     * @return 
     */
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Album) object).getId());
        } else {
            return null;
        }
    }
}
