/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.Survey;
import com.fractals.beans.SurveyChoice;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Renuchan
 */
@Named("surveyChoiceCon")
@SessionScoped
public class SurveyChoiceJpaController implements Serializable {

    public SurveyChoiceJpaController() 
    {}
    
    @Resource
    private UserTransaction utx;
   
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em;

    public void create(SurveyChoice surveyChoice) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            Survey survey = surveyChoice.getSurvey();
            if (survey != null) {
                survey = em.getReference(survey.getClass(), survey.getId());
                surveyChoice.setSurvey(survey);
            }
            em.persist(surveyChoice);
            if (survey != null) {
                survey.getSurveyChoices().add(surveyChoice);
                survey = em.merge(survey);
            }
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

    public void edit(SurveyChoice surveyChoice) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            SurveyChoice persistentSurveyChoice = em.find(SurveyChoice.class, surveyChoice.getId());
            Survey surveyOld = persistentSurveyChoice.getSurvey();
            Survey surveyNew = surveyChoice.getSurvey();
            if (surveyNew != null) {
                surveyNew = em.getReference(surveyNew.getClass(), surveyNew.getId());
                surveyChoice.setSurvey(surveyNew);
            }
            surveyChoice = em.merge(surveyChoice);
            if (surveyOld != null && !surveyOld.equals(surveyNew)) {
                surveyOld.getSurveyChoices().remove(surveyChoice);
                surveyOld = em.merge(surveyOld);
            }
            if (surveyNew != null && !surveyNew.equals(surveyOld)) {
                surveyNew.getSurveyChoices().add(surveyChoice);
                surveyNew = em.merge(surveyNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = surveyChoice.getId();
                if (findSurveyChoice(id) == null) {
                    throw new NonexistentEntityException("The surveyChoice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            SurveyChoice surveyChoice;
            try {
                surveyChoice = em.getReference(SurveyChoice.class, id);
                surveyChoice.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The surveyChoice with id " + id + " no longer exists.", enfe);
            }
            Survey survey = surveyChoice.getSurvey();
            if (survey != null) {
                survey.getSurveyChoices().remove(surveyChoice);
                survey = em.merge(survey);
            }
            em.remove(surveyChoice);
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

    public List<SurveyChoice> findSurveyChoiceEntities() {
        return findSurveyChoiceEntities(true, -1, -1);
    }

    public List<SurveyChoice> findSurveyChoiceEntities(int maxResults, int firstResult) {
        return findSurveyChoiceEntities(false, maxResults, firstResult);
    }

    private List<SurveyChoice> findSurveyChoiceEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SurveyChoice.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SurveyChoice findSurveyChoice(Integer id) {
        try {
            return em.find(SurveyChoice.class, id);
        } finally {
            em.close();
        }
    }

    public int getSurveyChoiceCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SurveyChoice> rt = cq.from(SurveyChoice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
