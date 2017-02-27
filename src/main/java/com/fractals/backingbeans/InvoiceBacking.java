package com.fractals.backingbeans;

import com.fractals.beans.Order;
import com.fractals.beans.User;
import com.fractals.controllers.LoginController;
import com.fractals.controllers.OrderJPAController;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
 * @version 26/02/2017
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
}
