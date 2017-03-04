package com.fractals.backingbeans;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * This class changes the locale of the current server session. Thus it switches
 * the language.
 * 
 * This class was taken from Ken Fogel's example.
 * Found at: https://gitlab.com/omniprof/JSFSample25ParameterEvents
 *
 * @author Danieil Skrinikov
 * @version 1.0.1
 * @since 2017-03-04
 */
@Named
@RequestScoped
public class LocaleChanger {
    
    private static final Logger log = Logger.getLogger("ReportsBacking.class");
    
    private static final long serialVersionUID = 1l;

    /**
     * Changes the locale of the current session. Thus provoking a language change.
     * 
     * @return 
     */
    public String changeLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        String languageCode = getLanguageCode(context);

        Locale aLocale;
        switch (languageCode) {
            case "en_CA":
                aLocale = Locale.CANADA;
                break;
            case "fr_CA":
                aLocale = Locale.CANADA_FRENCH;
                break;
            default:
                aLocale = Locale.getDefault();
        }
        context.getViewRoot().setLocale(aLocale);
        return null;
    }

    /**
     * Gets the language code from the http request.
     * 
     * @param context current context of the request
     * @return returns the language code.
     */
    private String getLanguageCode(FacesContext context) {
        Map<String, String> params = context.getExternalContext()
                .getRequestParameterMap();
        return params.get("languageCode");
    }
}
