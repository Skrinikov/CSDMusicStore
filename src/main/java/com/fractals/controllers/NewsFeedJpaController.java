/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.beans.NewsFeed;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *  This class will be responsible for performing all CRUD actions concerning 
 *  newsFeed objects
 * 
 * @author Renuchan
 */

@Named("nfJpaController")
@RequestScoped
public class NewsFeedJpaController implements Serializable {

    @Resource
    private UserTransaction utx = null;
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }
    
    public void create(NewsFeed newsFeed) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(newsFeed);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } 
    }

    public void edit(NewsFeed newsFeed) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            newsFeed = em.merge(newsFeed);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = newsFeed.getId();
                if (findNewsFeed(id) == null) {
                    throw new NonexistentEntityException("The newsFeed with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            NewsFeed newsFeed;
            try {
                newsFeed = em.getReference(NewsFeed.class, id);
                newsFeed.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The newsFeed with id " + id + " no longer exists.", enfe);
            }
            em.remove(newsFeed);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } 
    }

    public List<NewsFeed> findNewsFeedEntities() {
        return findNewsFeedEntities(true, -1, -1);
    }

    public List<NewsFeed> findNewsFeedEntities(int maxResults, int firstResult) {
        return findNewsFeedEntities(false, maxResults, firstResult);
    }

    private List<NewsFeed> findNewsFeedEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NewsFeed.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public NewsFeed findNewsFeed(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NewsFeed.class, id);
        } finally {
        }
    }

    public int getNewsFeedCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NewsFeed> rt = cq.from(NewsFeed.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }
    
}
