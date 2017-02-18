package com.fractals.controllers;

import com.fractals.backingbeans.UserBacking;
import com.fractals.beans.User;
import com.fractals.utilities.SecurityHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 * The controller responsible for register, login, and logout users.
 * Contains currently logged in user.
 *
 * @author Alena Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
@Named("login")
@SessionScoped
public class LoginController implements Serializable {

    private static transient final java.util.logging.Logger log = java.util.logging.Logger.getLogger("LoginController.class");
    private SecurityHelper security = new SecurityHelper();
    @Inject
    private UserBacking userBacking;
    private User currentUser;   
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;

    /**
     * Performs the necessary actions to login the user.
     */
    public void login() {
        currentUser = userBacking.getUser();
        try{
            User userDb = getByUsername(currentUser.getUsername());
            byte[] dbHash = userDb.getPassword();
            byte[] loginHash = security.hash(userBacking.getPassword(), userDb.getSalt());
            if(!security.isHashValid(dbHash, loginHash)) {
                FacesMessage message = new FacesMessage("Invalid username or password!");
                FacesContext.getCurrentInstance().addMessage(null, message);
                currentUser = null;
            }
            else {
                try{
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
                }
                catch(IOException io) {
                    log.log(Level.WARNING, "error when redirecting: {0}", io.getMessage());
                }
            }
        }
        catch(EntityNotFoundException | NonUniqueResultException | NoResultException ex) {
            FacesMessage message = new FacesMessage("Invalid username or password!");
            FacesContext.getCurrentInstance().addMessage(null, message);
            log.log(Level.WARNING,"invalid user: {0}", ex.getMessage());
            currentUser = null;
        }   
    }
    
    /**
     * Performs the necessary actions to register the user.
     */
    public void register() {
        User newUser = userBacking.getUser();
        try{
            getByUsername(newUser.getUsername());
            log.log(Level.INFO, "this user already exists");
            FacesMessage message = new FacesMessage("Such user already exists!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        catch(EntityNotFoundException | NoResultException ex) {
            String salt = security.getSalt();
            newUser.setSalt(salt);
            byte[] passHash = security.hash(userBacking.getPassword(), salt);
            newUser.setPassword(passHash);
            userBacking.saveUser();
            try{
                currentUser = newUser;
                FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
            }
            catch(IOException io) {
                log.log(Level.WARNING, "error when redirecting: {0}", io.getMessage());
            }
        }   
    }
    
    /**
     * Logs out the user.
     * @return null to stay on the same page.
     */
    public String logout() {
        currentUser = null;
        return null;
    }
    
    /**
     * Verifies if the user is logged in.
     * @return true if the user is logged in; false otherwise.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Returns the current user.
     * @return the current user.
     */
    public User getCurrentUser() {
        return currentUser;
   }
    
    /**
     * Searches users table by the username.
     * @param username The username to search by in the users table.
     * @return the user with the corresponding username.
     */
    private User getByUsername(String username) {
        return (User)entityManager.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
    }
    
    

}
