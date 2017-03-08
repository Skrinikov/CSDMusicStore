package com.fractals.converters;

import com.fractals.beans.Province;
import com.fractals.controllers.ProvinceController;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The class that is responsible for converting a Province object
 * to the String with its name.
 *
 * @author Aline Shulzhenko
 * @version 07/03/2017
 * @since 1.8
 */
@Named("ProvinceConverter")
@ApplicationScoped
public class ProvinceConverter implements Converter {
    @Inject
    private ProvinceController controller;

    /**
     * Converts a String with a Province name to the Province object.
     * @param context FacesContext object.
     * @param component UICompoment object.
     * @param value The string to be converted.
     * @return the Province object with the selected name.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Province> list = controller.getProvinces();
        System.out.println(Arrays.toString(list.toArray(new Province[]{})));
        Province chosen = null;
        for(Province p : list)
            if(p.getName().equals(value))
                chosen = p;
        if(chosen == null) {
            FacesMessage message = new FacesMessage(ResourceBundle.getBundle("Bundle").getString("invalid_province"));
            context.addMessage("registerForm:province", message);
        }
        return chosen;
    }

    /**
     * Converts a Province object to the String with its name.
     * @param context FacesContext object.
     * @param component UICompoment object.
     * @param value The object to be converted.
     * @return the name of the selected province as a string.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Province) {
            Province province = (Province)value;
            return province.getName();
        }
        else {
            FacesMessage message = new FacesMessage(ResourceBundle.getBundle("Bundle").getString("invalid_province"));
            context.addMessage("registerForm:province", message);
            return null;
        }
    }
    
}
