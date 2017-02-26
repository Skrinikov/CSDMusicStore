package com.fractals.utilities;

import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("DateConverter")
public class DateConverter extends DateTimeConverter {

    public DateConverter() {
        setPattern("MM/yyyy");
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
        super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        if (value != null && value.length() != getPattern().length()) {
            FacesMessage message = new FacesMessage(bundle.getString("invalid_format"));
            context.addMessage("checkoutForm:date", message);
        }

        return super.getAsObject(context, component, value);
    }

}