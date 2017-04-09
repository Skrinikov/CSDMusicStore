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
 *Class containing the methods and variables for managing Users
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
    /**
    * Boolean that indicates if the manager is editing or viewing the selectedUser
    */
    private boolean editable = false;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User u) {
        selectedUser = u;
        makeUneditable();
    }
    /**
     * @return a list of all the existing Users
     */
    public List<User> getUsers() {
        return userJpaController.findUserEntities();
    }
    /**
     * @return whether there are any users in the database
     */
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
    
    /**
     * Returns the total sales made by a user
     * @param u, the user passed down by the datatable
     * @return t
     */
    public Number getTotalSales(User u) {
        return userController.getTotalSalesByUser(u);
    }
    
    /**
     * Returns the total sales made by a user, 
     * after the user has been selected and displayed separately
     * @return 
     */
    public Number getTotalSales() {
        return getTotalSales(selectedUser);
    }

    public void edit() throws Exception {
        makeUneditable();
        userJpaController.edit(selectedUser);
    }
    
}
