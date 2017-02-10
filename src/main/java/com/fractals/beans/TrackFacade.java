/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 1710030
 */
@Stateless
public class TrackFacade extends AbstractFacade<Track> {

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrackFacade() {
        super(Track.class);
    }
    
}
