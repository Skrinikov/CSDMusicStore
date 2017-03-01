package com.fractals.backingbeans;

import com.fractals.beans.Order;
import com.fractals.beans.User;
import com.fractals.controllers.LoginController;
import com.fractals.controllers.OrderJPAController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The class that is responsible for the Invoice page logic and backing
 * the appropriate page.
 * It submits the order, sends an email to the customer and displays
 * the receipt.
 *
 * @author Aline Shulzhenko
 * @version 28/02/2017
 * @since 1.8
 */
@Named("invoice")
@RequestScoped
public class InvoiceBacking {
    @Inject
    private LoginController login;
    @Inject
    private OrderJPAController orders;
    @Inject
    private ShoppingCart cart;
    
    private User user;
    private Order order;
    private boolean cartEmptyBeforeInvoice = false;

    /**
     * Returns true if the cart was empty when this page was accessed (i.e. no
     * order was submitted).
     * @return true if the cart was empty when this page was accessed; false otherwise.
     */
    public boolean isCartEmptyBeforeInvoice() {
        return cartEmptyBeforeInvoice;
    }
    
    /**
     * Initializes fields.
     */
    @PostConstruct
    public void init() {
        user = login.getCurrentUser();
        if(!cart.isEmpty() && user != null)
            order = orders.submitOrder(cart, user);
        else
            cartEmptyBeforeInvoice = true;
    }
    
    /**
     * Returns true if there was an error when submitting the order
     * and error message needs to be displayed.
     * @return true if there was an error when submitting the order; false otherwise.
     */
    public boolean showOrderSubmitError() {
        return order == null && !cartEmptyBeforeInvoice && user != null;
    }
    
    /**
     * Returns the current order for this user.
     * @return the current order for this user.
     */
    public Order getOrder() {
        return order;
    }
    
    /**
     * Returns currently logged in user.
     * @return currently logged in user.
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Formats the date into user-friendly format.
     * @param local The date to be formatted.
     * @return the string with the formatted date.
     */
    public String formatDate(LocalDateTime local) {
        return local.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss a", Locale.CANADA));
    }
    
    /**
     * Formats the number to two decimal places.
     * @param number The number to format.
     * @return the number formatted to two decimal places.
     */
    public double formatNumber(double number) {
        return Math.round(number*100)/100.0;
    }
    
    /**
     * Formats the taxes number to three decimal places and returns the percent value 
     * (the original number is multiplied by 100).
     * @param number The number to format.
     * @return the number formatted to three decimal places and in percent value.
     */
    public double formatTaxes(double number) {
        return Math.round(number*100000)/1000.0;
    }
}
