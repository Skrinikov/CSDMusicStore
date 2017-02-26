package com.fractals.backingbeans;

import com.fractals.beans.Order;
import com.fractals.beans.User;
import com.fractals.controllers.LoginController;
import com.fractals.controllers.OrdersController;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
public class InvoiceBacking {
    @Inject
    private LoginController login;
    @Inject
    private OrdersController orders;
    @Inject
    private ShoppingCart cart;
    
    private User user;
    private Order order;
    
    /**
     * Initializes fields.
     */
    @PostConstruct
    public void init() {
        user = login.getCurrentUser();
        order = orders.submitOrder(cart, user);
    }
}
