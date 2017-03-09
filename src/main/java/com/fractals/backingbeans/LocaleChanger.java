package com.fractals.backingbeans;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

/**
 * This class changes the locale of the current server session. Thus it switches
 * the language.
 *
 * This class was taken from Ken Fogel's example. Found at:
 * https://gitlab.com/omniprof/JSFSample25ParameterEvents
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

    private boolean isCookieJustWritten = false;

    /**
     * Changes the locale of the current session. Thus provoking a language
     * change.
     *
     * @return
     */
    public String changeLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        String languageCode = getLanguageCode(context);

        log.info(languageCode);
        writeLocaleCookie(languageCode);
        return setLocale(languageCode, context);
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

    /**
     * Checks if the locale cookie is set and if so sets the appropriate session
     * locale.
     */
    public void checkCookieLocale() {
        if (!isCookieJustWritten) {
            isCookieJustWritten = false;
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, Object> cookieMap = context.getExternalContext().getRequestCookieMap();

            Object localeCookie = cookieMap.get("localeCookie");

            if (localeCookie != null) {
                log.info("Cookie Locale");
                setLocale(((Cookie) localeCookie).getValue(), context);
            }
        }

    }

    /**
     * Sets the locale with the specified value and then writes a cookie with
     * the locale
     *
     * @param languageCode locale code.
     * @param context where to change the locale.
     * @return where to go after.
     */
    private String setLocale(String languageCode, FacesContext context) {
        log.info(languageCode);

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
        log.info("Locale-" + aLocale.getLanguage());

        return null;
    }

    /**
     * Writes a cookie with the given locale for the session.
     *
     * @param code content of the cookie
     */
    public void writeLocaleCookie(String code) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.getExternalContext().addResponseCookie("localeCookie", code, null);
        isCookieJustWritten = true;
        log.info("cookie written");
    }
}
