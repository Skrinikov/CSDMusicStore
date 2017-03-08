/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;


/**
 *
 * @author 1710030
 */
@RequestScoped
@Named("localDateConverter")
public class LocalDateConverter implements Converter {
    
      public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {                                 
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(value, formatter);        
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }     
        } else 
            return null;       
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = (LocalDate) object;
            return date.format(formatter);
      
        } else 
            return null;        
    }
}




