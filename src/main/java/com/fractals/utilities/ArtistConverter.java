/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.utilities;

import com.fractals.backingbeans.ArtistBackingBean;
import com.fractals.beans.Artist;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Sarah
 */
@FacesConverter("artistConverter")
public class ArtistConverter implements Converter {

    
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + value);
        if (value != null && value.trim().length() > 0) {
            try {
                ArtistBackingBean service = (ArtistBackingBean) fc.getExternalContext().getApplicationMap().get("theArtists");
                if (service == null) {
                    System.out.println(">>>>>>>>>>>>>>>service == null");
                }
                if (service.getArtists() == null) {
                    System.out.println(">>>>>>>>>>>>>>>service.getArtists() == null");
                }

                return service.getArtists().get(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Artist) object).getId());
        } else {
            return null;
        }
    }
}
