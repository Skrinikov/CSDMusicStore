package com.fractals.controllers;

import com.fractals.backingbeans.UserBacking;
import com.fractals.beans.Province;
import com.fractals.beans.User;
import com.fractals.utilities.SecurityHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * logs in users
 * @author lynn
 */
@Named("login")
@SessionScoped
public class LoginController implements Serializable {

    private final transient Logger log = LoggerFactory.getLogger(getClass().getName());
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
            byte[] db = userDb.getPassword().getBytes();
            byte[] login = security.hash(currentUser.getPassword(), currentUser.getSalt());
            if(!security.isHashValid(db, login)) {
                FacesMessage message = new FacesMessage("Invalid username or password+!");
                FacesContext.getCurrentInstance().addMessage(null, message);
                currentUser = null;
            }
        }
        catch(EntityNotFoundException | NonUniqueResultException | NoResultException ex) {
            FacesMessage message = new FacesMessage("Invalid username or password!");
            FacesContext.getCurrentInstance().addMessage(null, message);
            log.error("invalid user", ex);
            currentUser = null;
        }   
    }
    
    public boolean register() {
        User newUser = userBacking.getUser();
        try{
            getByUsername(newUser.getUsername());
            log.error("this user already exists");
            FacesMessage message = new FacesMessage("Such user already exists!");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return false;
        }
        catch(EntityNotFoundException | NoResultException ex) {
            String salt = security.getSalt();
            newUser.setSalt(salt);
            byte[] passHash = security.hash(newUser.getPassword(), salt);
            newUser.setPassword(new String(passHash));
            userBacking.saveUser();
            try{
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                return true;
            }
            catch(IOException io) {
                log.error("error when redirecting", io);
                return false;
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
