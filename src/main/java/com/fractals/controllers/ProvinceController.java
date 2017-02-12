/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.beans.Province;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lynn
 */
@Named("Province")
@SessionScoped
public class ProvinceController implements Serializable {
    private List<Province> provinces;
        
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
        
    public List<Province> getProvinces() {
        return provinces;
    }
    
    @PostConstruct
    public void init() {
        provinces = (List<Province>)entityManager.createNamedQuery("Province.findAll").getResultList();
    }
}
