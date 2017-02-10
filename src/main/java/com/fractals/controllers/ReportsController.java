package com.fractals.controllers;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
    private Date date1;
    
    @Resource
    private UserTransaction userTransaction;
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    public void onDateSelect(SelectEvent event) {
        Date date = (Date)event.getObject();
        System.out.println(date+"argh");
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }
    
    public void click(){
        System.out.println("Ha");
    }
}
