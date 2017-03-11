package com.fractals.backingbeans;

import com.fractals.beans.User;
import com.fractals.controllers.UserJpaController;
import com.fractals.utilities.BundleLocaleResolution;
import com.fractals.utilities.SecurityHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * The controller responsible for register, login, and logout users.
 * Contains currently logged in user.
 *
 * @author Aline Shulzhenko
 * @version 08/03/2017
 * @since 1.8
 */
@Named("login")
@SessionScoped
public class LoginBacking implements Serializable {

    private static transient final java.util.logging.Logger log = java.util.logging.Logger.getLogger("LoginBacking.class");
    private final SecurityHelper security = new SecurityHelper();
    @Inject
    private UserBacking userBacking;
    @Inject
    private UserJpaController userController;
    
    private User currentUser;   

    /**
     * Performs the necessary actions to login the user.
     */
    public void login() {
        currentUser = userBacking.getUser();
        try{
            User userDb = userController.getByUsername(currentUser.getUsername());
            byte[] dbHash = userDb.getPassword();
            byte[] loginHash = security.hash(userBacking.getPassword(), userDb.getSalt());
            if(!security.isHashValid(dbHash, loginHash)) {
                FacesMessage message = new FacesMessage(new BundleLocaleResolution().returnBundleWithCurrentLocale().getString("invalid_uname_or_pass"));
                FacesContext.getCurrentInstance().addMessage("loginForm", message);
                currentUser = null;
            }
            else {
                try{
                    currentUser = userDb;
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
                }
                catch(IOException io) {
                    log.log(Level.WARNING, "error when redirecting: {0}", io.getMessage());
                }
            }
        }
        catch(EntityNotFoundException | NonUniqueResultException | NoResultException ex) {
            FacesMessage message = new FacesMessage(new BundleLocaleResolution().returnBundleWithCurrentLocale().getString("invalid_uname_or_pass"));
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
            userController.getByUsername(newUser.getUsername());
            log.log(Level.INFO, "this user already exists");
            FacesMessage message = new FacesMessage(new BundleLocaleResolution().returnBundleWithCurrentLocale().getString("user_exists_error"));
            FacesContext.getCurrentInstance().addMessage("registerForm:username", message);
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
     * Logs out the user. If the user is an admin, for security, the user
     * is redirected to the client index page. Otherwise, the user stays on the same page.
     */
    public void logout() {
        if(currentUser != null && currentUser.getIsAdmin()) {           
            try {
                currentUser = null;
                FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
            } 
            catch (IOException io) {
                log.log(Level.WARNING, "error when redirecting: {0}", io.getMessage());
            }
        }
        else
            currentUser = null;
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
     * Refreshes the current user state to match the database.
     */
    public void refreshCurrenthUser() {
        userController.refreshUser(currentUser);
    }
    
}
