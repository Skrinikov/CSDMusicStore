package com.fractals.controllers;

import com.fractals.beans.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import org.primefaces.event.SelectEvent;

/**
 * This controller provides basic querying to generate reports about the database
 * and then display the generated data on a page.
 * 
 * @author Danieil Skrinikov
 * @version 0.0.01
 * @since 2017-02-09
 */
@Named("reportsController")
@RequestScoped
public class ReportsController implements Serializable {   
    
    @Resource
    private UserTransaction userTransaction;
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    // Queries
    private static final String zeroUserQuery = "SELECT u FROM User u LEFT JOIN u.ordersCollection o WHERE o.userId IS NULL";
    
    /**
     * 
     * @return 
     */
    public List<User> getZeroClients(){
        List<User> users = new ArrayList<>();
        
        TypedQuery<User> query = entityManager.createQuery(zeroUserQuery,User.class);
        users = query.getResultList();
        
        return users;
    }
    
}
