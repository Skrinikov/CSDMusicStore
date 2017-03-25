package com.fractals.controllers;

import com.fractals.beans.Province;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The controller responsible to work with the province object.
 *
 * @author Aline Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
@Named("Province")
@RequestScoped
public class ProvinceController implements Serializable {
    private List<Province> provinces;
        
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
       
    /**
     * Returns the list of all provinces.
     * @return the list of all provinces.
     */
    public List<Province> getProvinces() {
        return provinces;
    }
    
    /**
     * Gets the list of all provinces from the database.
     */
    @PostConstruct
    public void init() {
        provinces = (List<Province>)entityManager.createNamedQuery("Province.findAll").getResultList();
    }
}
