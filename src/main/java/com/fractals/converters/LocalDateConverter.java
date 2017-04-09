package com.fractals.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;


/**
 * Converter to get the LocalDate Object that corresponds to a formated string
 * @author MOUFFOK Sarah
 */
@RequestScoped
@Named("localDateConverter")
public class LocalDateConverter implements Converter {
    
    /**
     * Get the LocalDate from the formatted String
     * @param fc
     * @param uic
     * @param value, the String
     * @return the LocalDate
     */
      public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {                                 
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(value, formatter);        
            } catch (Exception e) {
                throw new ConverterException();
            }     
        } else 
            return null;       
    }

    /**
     * Get the String value of the Local Date
     * @param fc
     * @param uic
     * @param object, the LocalDate
     * @return the String
     */
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = (LocalDate) object;
            return date.format(formatter);
        } else 
            return null;        
    }
}




