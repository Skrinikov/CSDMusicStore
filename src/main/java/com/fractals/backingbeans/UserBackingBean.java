/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Order;
import com.fractals.beans.User;
import com.fractals.controllers.UserJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 1710030
 */

@Named("theUsers")
@SessionScoped
public class UserBackingBean implements Serializable {
    
    private User selectedUser;
    public User getSelectedUser(){ return selectedUser;}
    public void setSelectedUser(User u){ selectedUser = u; makeUneditable();}
    
    @Inject
    private UserJpaController userJpaController;
    
    public List<User> getUsers(){
        return userJpaController.findUserEntities();
    }
    
    public boolean isEmpty(){
        return userJpaController.isEmpty();
    }
    
    private boolean editable;
    public boolean getEditable(){ return editable; }
    public void setEditable(boolean b){editable = b;}
    public void makeEditable(){setEditable(true);}
    public void makeUneditable(){setEditable(false);}
    
    public String edit() throws Exception {
        userJpaController.edit(selectedUser);
        editable = false;
        return "/management/user/usersViewEdit.xhtml";
        
    }
    
    public Number getTotalSales(){
        return getTotalSalesByUser(selectedUser);
    }
    
    public Number getTotalSalesByUser(User user){
        CriteriaBuilder cb = userJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        Root<Order> root = query.from(Order.class);
        query.select(cb.sum(root.get("netCost")));
        query.where(cb.equal(root.get("user"), user));
        Number result = userJpaController.getEntityManager().createQuery(query).getSingleResult();
        return result == null ? 0 : result;
        
    }
}
