package com.fractals.controllers;

import com.fractals.beans.BannerAd;
import com.fractals.beans.BannerAd_;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Sarah, Renuchan
 */
@Named("bannerAdJpaController")
@RequestScoped
public class BannerAdJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(BannerAd bannerAd) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(bannerAd);
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

    public void edit(BannerAd bannerAd) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            bannerAd = em.merge(bannerAd);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bannerAd.getId();
                if (findBannerAd(id) == null) {
                    throw new NonexistentEntityException("The bannerAd with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            BannerAd bannerAd;
            try {
                bannerAd = em.getReference(BannerAd.class, id);
                bannerAd.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bannerAd with id " + id + " no longer exists.", enfe);
            }
            em.remove(bannerAd);
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

    public List<BannerAd> findBannerAdEntities() {
        return findBannerAdEntities(true, -1, -1);
    }

    public List<BannerAd> findBannerAdEntities(int maxResults, int firstResult) {
        return findBannerAdEntities(false, maxResults, firstResult);
    }

    private List<BannerAd> findBannerAdEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(BannerAd.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public BannerAd findBannerAd(Integer id) {
        return em.find(BannerAd.class, id);
    }

    public int getBannerAdCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<BannerAd> rt = cq.from(BannerAd.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public boolean isEmpty(){
        return getBannerAdCount() == 0;
    }
    
    /**
     * This method will obtain all visible ads from the database. 
     * @author Renuchan
     * @return 
     */
    public List<BannerAd> getVisible(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BannerAd> cq = cb.createQuery(BannerAd.class); 
        Root<BannerAd> ad = cq.from(BannerAd.class); 
        cq.select(ad);
        cq.where(cb.isTrue(ad.get(BannerAd_.visible)));      
        return em.createQuery(cq).getResultList();     
    }    
}
