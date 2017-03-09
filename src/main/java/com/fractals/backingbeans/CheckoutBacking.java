package com.fractals.backingbeans;

import com.fractals.utilities.BundleLocaleResolution;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The class that is responsible for the Checkout page logic and backing
 * the appropriate page.
 *
 * @author Aline Shulzhenko
 * @version 26/02/2017
 * @since 1.8
 */
@Named("checkout")
@RequestScoped
public class CheckoutBacking {
    @Inject
    private CreditCard credit;
    
    private List<String> brands;
    private boolean isVisa = false;
    private ResourceBundle bundle;
    private boolean error = false;
    private static transient final java.util.logging.Logger log = java.util.logging.Logger.getLogger("CheckoutBacking.class");
    
    
    /**
     * Initializes fields.
     */
    @PostConstruct
    public void init() {
        brands = new ArrayList<>();
        brands.add("Visa");
        brands.add("MasterCard");
        bundle = new BundleLocaleResolution().returnBundleWithCurrentLocale();
        credit.setBrand("MasterCard");
    }

    /**
     * Returns the list of all supported credit card brands.
     * @return the list of all supported credit card brands.
     */
    public List<String> getBrands() {
        return brands;
    }
    
    /**
     * If the selected brand if Visa, triggers an addition of new field to the form.
     * @param brand the credit card brand.
     */
    public void addField(String brand) {
        isVisa = brand.equals("Visa");
        credit.setBrand(brand);
    }

    /**
     * Returns true if the customer chose to pay with the Visa credit card.
     * @return true if the customer chose to pay with the Visa credit card; false otherwise.
     */
    public boolean isIsVisa() {
        return isVisa;
    }
    
    /**
     * Validates user-submitted information. If the information is valid, the user
     * is redirected to the invoice page; if it is not, the errors are displayed.
     * @param brand The brand of the credit card provided.
     */
    public void validate(String brand) {
        error = false;
        if(!brands.contains(brand)) {
            FacesMessage message = new FacesMessage(bundle.getString("credit_brand_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm", message);
            error = true;
        }
        String number = credit.getNumber();
        if(!number.matches("^\\s*[0-9][0-9\\s]*$") || !luhnCheck(number.replaceAll("\\D", ""))) {
            FacesMessage message = new FacesMessage(bundle.getString("credit_number_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm:number", message);
            error = true;
        }
        LocalDate date = credit.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        if(date.isBefore(now) || (date.getMonth() == now.getMonth() && date.getYear() == now.getYear())) {           
            FacesMessage message = new FacesMessage(bundle.getString("credit_date_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm:date", message);
            error = true;
        }
        String name = credit.getName();
        if(name == null || name.trim().isEmpty()) {
            FacesMessage message = new FacesMessage(bundle.getString("credit_name_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm:name", message);
            error = true;
        }
        String code = credit.getCode();
        if(isVisa && (code == null || !code.matches("^[0-9][0-9][0-9]$"))) {
            FacesMessage message = new FacesMessage(bundle.getString("credit_code_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm:code", message);
            error = true;
        }
        if(!error) {
            try{
                FacesContext.getCurrentInstance().getExternalContext().redirect("invoice.xhtml");
            }
            catch(IOException io) {
                log.log(Level.WARNING, "error when redirecting: {0}", io.getMessage());
            }
        }
    }
    
    /**
     * Validates if the provided card number is valid.
     * @param cardNumber the card number.
     * @return true if the card number is valid; false otherwise.
     */
    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        for (int i = cardNumber.length() - 1; i >= 0; i -= 2) {
            sum += Integer.parseInt(cardNumber.substring(i, i + 1));
            if (i > 0) {
                int d = 2 * Integer.parseInt(cardNumber.substring(i - 1, i));
                if (d > 9) {
                    d -= 9;
                }
                sum += d;
            }
        }
        return sum % 10 == 0;
    }
}
