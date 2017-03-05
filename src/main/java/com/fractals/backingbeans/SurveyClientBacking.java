/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Survey;
import com.fractals.beans.SurveyChoice;
import com.fractals.controllers.SurveyChoiceJpaController;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Renuchan
 */
@Named("surveyCBacking")
@SessionScoped
public class SurveyClientBacking implements Serializable{
    
    
    private Survey sur; 
    private boolean isVoted = false;
    private static final String ANSWERS = "/WEB-INF/sections/survey/answers.xhtml";
    private static final String OPTIONS = "/WEB-INF/sections/survey/options.xhtml";
    
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em;
    
    @Inject 
    private SurveyChoiceJpaController scc; 
    
    
    private void getRandomSurvey()   
    {
        String query = "Select s From Survey s Where s.visible = TRUE"; 
        
        TypedQuery<Survey> sur = em.createQuery(query, Survey.class);
        List<Survey> list = sur.getResultList();
        Survey surveyPicked = null; 
        
        if(list != null && list.size() > 0)
        {
            int index = (int)(Math.random() * list.size());            
            surveyPicked = list.get(index);
        }
        
        this.sur = surveyPicked; 
        
    }
    
    /**
     * Renuchan,this is not ajax ready. -Danieil
     */
    public void selectedOption(SurveyChoice sc)
    {
        sc.setNumVotes(sc.getNumVotes() + 1);
        
        try {
            scc.edit(sc);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(SurveyClientBacking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SurveyClientBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        //reset the survey
        sur = null; 
        
        isVoted = true;
       
    }
    
    public Survey getSurvey()
    {
        if(sur == null)
            getRandomSurvey();
        return sur;       
    }
    
    
    /**
     * Checks if the user has already voted. If he did, then it returns the answers interface.
     * If he did not, returns the option's UI
     * 
     * @author: Danieil Skrinikov
     * 
     * @return path to a section.
     */
    public String getSurveryOptionsOrAnswers(){
        if(isVoted == false ){
            return OPTIONS;
        }
        return ANSWERS;
    }    
    
}
