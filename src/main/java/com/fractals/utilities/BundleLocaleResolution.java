package com.fractals.utilities;

import com.fractals.backingbeans.LocaleChanger;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

/**
 * BundleLocaleResolution class is responsible for getting the ResourceBundle
 * corresponding to the user's current locale.
 *
 * @author Aline Shulzhenko
 * @version 08/03/2017
 * @since 1.8
 */
public class BundleLocaleResolution {
    
    /**
     * Returns the bundle corresponding to the current locale saved in a cookie.
     * @return the bundle corresponding to the current locale saved in a cookie.
     */
    public ResourceBundle returnBundleWithCurrentLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookieMap = context.getExternalContext().getRequestCookieMap();
       
        Object localeCookie = cookieMap.get("localeCookie");
        
        if(localeCookie != null){
            String locale = ((Cookie) localeCookie).getValue();
            if(locale.equals("en_CA"))
                return ResourceBundle.getBundle("Bundle", Locale.CANADA);
            else if(locale.equals("fr_CA"))
                return ResourceBundle.getBundle("Bundle", Locale.CANADA_FRENCH);     
        }
        return ResourceBundle.getBundle("Bundle");
    }
}
