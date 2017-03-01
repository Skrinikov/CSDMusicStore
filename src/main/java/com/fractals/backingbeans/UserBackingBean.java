/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.User;
import com.fractals.controllers.UserJpaController;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1710030
 */

@Named("theUsers")
@RequestScoped
public class UserBackingBean {
    
    private User selectedUser;
    public User getSelectedUser(){ return selectedUser;}
    public void setSelectedUser(User u){ selectedUser = u;}
    
    @Inject
    private UserJpaController userJpaController;
    
    public List<User> getUsers(){
        return userJpaController.findUserEntities();
    }
    
    public boolean isEmpty(){
        return userJpaController.isEmpty();
    }
    
}
