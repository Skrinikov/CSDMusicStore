package com.fractals.backingbeans;

import com.fractals.beans.Province;
import com.fractals.beans.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
/**
 * used for jsf injections
 * @author lynn
 */
@Named("User")
@RequestScoped
@Transactional
public class UserBacking implements Serializable {
    private User user;
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    public User getUser() {
        return user == null ? new User() : user;
    }
    
    public void saveUser() {
        System.out.println(user);
        entityManager.persist(user);
    }
    
    @PostConstruct
    public void init() {
       user = new User();
    }
   
}
