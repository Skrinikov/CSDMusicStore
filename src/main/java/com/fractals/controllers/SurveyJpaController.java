
package com.fractals.controllers;

import com.fractals.beans.Survey;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.SurveyChoice;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *  This controller will be used to obtain and enter new surveys 
 * 
 * @author Renuchan
 */

@Named("surveyController")
@SessionScoped
public class SurveyJpaController implements Serializable {

    public SurveyJpaController() 
    {}
    
    @Resource
    private UserTransaction utx;
   
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em;

    

    public void create(Survey survey) throws RollbackFailureException, Exception {
        if (survey.getSurveyChoices() == null) {
            survey.setSurveyChoices(new ArrayList<SurveyChoice>());
        }
        try {
            utx.begin();
            List<SurveyChoice> attachedSurveyChoices = new ArrayList<SurveyChoice>();
            for (SurveyChoice surveyChoicesSurveyChoiceToAttach : survey.getSurveyChoices()) {
                surveyChoicesSurveyChoiceToAttach = em.getReference(surveyChoicesSurveyChoiceToAttach.getClass(), surveyChoicesSurveyChoiceToAttach.getId());
                attachedSurveyChoices.add(surveyChoicesSurveyChoiceToAttach);
            }
            survey.setSurveyChoices(attachedSurveyChoices);
            em.persist(survey);
            for (SurveyChoice surveyChoicesSurveyChoice : survey.getSurveyChoices()) {
                Survey oldSurveyOfSurveyChoicesSurveyChoice = surveyChoicesSurveyChoice.getSurvey();
                surveyChoicesSurveyChoice.setSurvey(survey);
                surveyChoicesSurveyChoice = em.merge(surveyChoicesSurveyChoice);
                if (oldSurveyOfSurveyChoicesSurveyChoice != null) {
                    oldSurveyOfSurveyChoicesSurveyChoice.getSurveyChoices().remove(surveyChoicesSurveyChoice);
                    oldSurveyOfSurveyChoicesSurveyChoice = em.merge(oldSurveyOfSurveyChoicesSurveyChoice);
                }
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

    public void edit(Survey survey) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Survey persistentSurvey = em.find(Survey.class, survey.getId());
            List<SurveyChoice> surveyChoicesOld = persistentSurvey.getSurveyChoices();
            List<SurveyChoice> surveyChoicesNew = survey.getSurveyChoices();
            List<SurveyChoice> attachedSurveyChoicesNew = new ArrayList<SurveyChoice>();
            for (SurveyChoice surveyChoicesNewSurveyChoiceToAttach : surveyChoicesNew) {
                surveyChoicesNewSurveyChoiceToAttach = em.getReference(surveyChoicesNewSurveyChoiceToAttach.getClass(), surveyChoicesNewSurveyChoiceToAttach.getId());
                attachedSurveyChoicesNew.add(surveyChoicesNewSurveyChoiceToAttach);
            }
            surveyChoicesNew = attachedSurveyChoicesNew;
            survey.setSurveyChoices(surveyChoicesNew);
            survey = em.merge(survey);
            for (SurveyChoice surveyChoicesOldSurveyChoice : surveyChoicesOld) {
                if (!surveyChoicesNew.contains(surveyChoicesOldSurveyChoice)) {
                    surveyChoicesOldSurveyChoice.setSurvey(null);
                    surveyChoicesOldSurveyChoice = em.merge(surveyChoicesOldSurveyChoice);
                }
            }
            for (SurveyChoice surveyChoicesNewSurveyChoice : surveyChoicesNew) {
                if (!surveyChoicesOld.contains(surveyChoicesNewSurveyChoice)) {
                    Survey oldSurveyOfSurveyChoicesNewSurveyChoice = surveyChoicesNewSurveyChoice.getSurvey();
                    surveyChoicesNewSurveyChoice.setSurvey(survey);
                    surveyChoicesNewSurveyChoice = em.merge(surveyChoicesNewSurveyChoice);
                    if (oldSurveyOfSurveyChoicesNewSurveyChoice != null && !oldSurveyOfSurveyChoicesNewSurveyChoice.equals(survey)) {
                        oldSurveyOfSurveyChoicesNewSurveyChoice.getSurveyChoices().remove(surveyChoicesNewSurveyChoice);
                        oldSurveyOfSurveyChoicesNewSurveyChoice = em.merge(oldSurveyOfSurveyChoicesNewSurveyChoice);
                    }
                }
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
                Integer id = survey.getId();
                if (findSurvey(id) == null) {
                    throw new NonexistentEntityException("The survey with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Survey survey;
            try {
                survey = em.getReference(Survey.class, id);
                survey.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The survey with id " + id + " no longer exists.", enfe);
            }
            List<SurveyChoice> surveyChoices = survey.getSurveyChoices();
            for (SurveyChoice surveyChoicesSurveyChoice : surveyChoices) {
                surveyChoicesSurveyChoice.setSurvey(null);
                surveyChoicesSurveyChoice = em.merge(surveyChoicesSurveyChoice);
            }
            em.remove(survey);
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

    public List<Survey> findSurveyEntities() {
        return findSurveyEntities(true, -1, -1);
    }

    public List<Survey> findSurveyEntities(int maxResults, int firstResult) {
        return findSurveyEntities(false, maxResults, firstResult);
    }

    private List<Survey> findSurveyEntities(boolean all, int maxResults, int firstResult) {
      
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Survey.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        
    }

    public Survey findSurvey(Integer id) {
        
            return em.find(Survey.class, id);
      
    }

    public int getSurveyCount() {
        
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Survey> rt = cq.from(Survey.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
      
    }
    
}
