package com.fractals.backingbeans;

import com.fractals.controllers.OrdersController;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
 * @version 25/02/2017
 * @since 1.8
 */
@Named("checkout")
@RequestScoped
public class CheckoutBacking {
    @Inject
    private OrdersController ordersController;
    @Inject
    private CreditCard credit;
    @Inject
    private ShoppingCart shopCart;
    
    private List<String> brands;
    private boolean isVisa;
    private ResourceBundle bundle;
    
    
    /**
     * Initializes fields.
     */
    @PostConstruct
    public void init() {
        brands = new ArrayList<>();
        brands.add("Visa");
        brands.add("MasterCard");
        isVisa = false;
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
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
     * @return the name of the page where the user is redirected.
     */
    public String validate() {
        if(!brands.contains(credit.getBrand())) {
            FacesMessage message = new FacesMessage(bundle.getString("credit_brand_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm", message);
            return null;
        }
        String number = credit.getNumber();
        if(!number.matches("^\\s*[0-9][0-9\\s]*$") && !luhnCheck(number.replaceAll("\\D", ""))) {
            FacesMessage message = new FacesMessage(bundle.getString("credit_number_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm:number", message);
            return null;
        }
        if(credit.getName().isEmpty()) {
            FacesMessage message = new FacesMessage(bundle.getString("credit_name_error"));
            FacesContext.getCurrentInstance().addMessage("checkoutForm:name", message);
            return null;
        }
        
        return "invoice";
    }
    
    /**
     * Validates if the provided card number is valid.
     * @param cardNumber the card number.
     * @return true if the card number is true; false otherwise.
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
