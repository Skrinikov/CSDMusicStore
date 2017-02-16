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
 * Responsible for register or log in users.
 * @author Alena Shulzhenko
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

    public void login() {
        currentUser = userBacking.getUser();
        try{
            User userDb = getByUsername(currentUser.getUsername());
            byte[] db = userDb.getPassword();
            byte[] login = security.hash(userBacking.getPassword(), userDb.getSalt());
            if(!security.isHashValid(db, login)) {
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
                FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
            }
            catch(IOException io) {
                log.log(Level.WARNING, "error when redirecting: {0}", io.getMessage());
            }
        }   
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public User getCurrentUser() {
        return currentUser;
   }
    
    private List<User> getUsers() {
        return (List<User>)entityManager.createNamedQuery("User.findAll").getResultList();
    }
    
    private User getByUsername(String username) {
        return (User)entityManager.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
    }

    private User getByEmail(String email) {
        return (User)entityManager.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
    }
    
    

}
