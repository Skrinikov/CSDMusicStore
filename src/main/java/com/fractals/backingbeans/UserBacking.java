package com.fractals.backingbeans;

import com.fractals.beans.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * A backing bean responsible to contain the currently logged in user and
 * save new users to the database.
 *
 * @author Aline Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
@Named("User")
@RequestScoped
@Transactional
public class UserBacking implements Serializable {
    private User user;
    private String password;
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    /**
     * Returns the user.
     * @return the user object.
     */
    public User getUser() {
        return user == null ? new User() : user;
    }
    
    /**
     * Returns the password for this user.
     * @return the password for this user.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password for this user.
     * @param password the password for this user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Saves this user to the database.
     */
    public void saveUser() {
        entityManager.persist(user);
    }
    
    /**
     * Initializes fields.
     */
    @PostConstruct
    public void init() {
       user = new User();
       password = "";
    }
   
}
