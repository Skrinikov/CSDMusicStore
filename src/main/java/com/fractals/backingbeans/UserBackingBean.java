/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.User;
import com.fractals.controllers.UserController;
import com.fractals.controllers.UserJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named("theUsers")
@SessionScoped
public class UserBackingBean implements Serializable {

    @Inject
    private UserJpaController userJpaController;
    @Inject
    private UserController userController;
    private User selectedUser;
    private boolean editable = false;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User u) {
        selectedUser = u;
        makeUneditable();
    }

    public List<User> getUsers() {
        return userJpaController.findUserEntities();
    }

    public boolean isEmpty() {
        return userJpaController.isEmpty();
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean b) {
        editable = b;
    }

    public void makeEditable() {
        setEditable(true);
    }

    public void makeUneditable() {
        setEditable(false);
    }

    public Number getTotalSales(User u) {
        return userController.getTotalSalesByUser(u);
    }
    
    public Number getTotalSales() {
        return getTotalSales(selectedUser);
    }

    public void edit() throws Exception {
        makeUneditable();
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
        userJpaController.edit(selectedUser);
    }

}
