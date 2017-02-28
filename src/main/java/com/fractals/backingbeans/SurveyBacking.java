
package com.fractals.backingbeans;

import com.fractals.beans.Survey;
import com.fractals.beans.SurveyChoice;
import com.fractals.controllers.SurveyJpaController;
import com.fractals.controllers.SurveyChoiceJpaController;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.TypedQuery;
/**
 *
 * This bean will be used to obtain a visible survey and it and its options to 
 * its xhtml file
 * 
 * @author Renuchan
 */

@Named("surveyBacking")
@SessionScoped
public class SurveyBacking implements Serializable {
    
    private Integer surEditId; 
    private Survey current;
    private SurveyChoice currentSC; 
    
    @Inject
    private SurveyJpaController sc; 
    
    @Inject
    private SurveyChoiceJpaController surChoiceCon; 
      
    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager em;
    
    
    public Survey getSurvey()
    {
        //Will use the criteria builder to get the survey 
        
        String query = "select s from Survey s where s.visible = true"; 
        
        TypedQuery<Survey> sur = em.createQuery(query, Survey.class); 
        List<Survey> list = sur.getResultList();
        return getRandomSur(list);                
    }
    
    private Survey getRandomSur(List<Survey> sur)
    {
        int size = sur.size(); 
        int index = (int)(Math.random() + 0) * size;
        return sur.get(index);        
    }
    
    
    
    
    /**
     * This method will be used to load the survey information and present 
     * the manager with the survey edit page so he can edited it. 
     */
    public String redirectToSurveyEdit(Integer id)
    {
        this.surEditId = id;
        this.current = sc.findSurvey(surEditId);
        return "/survey/Edit.xhtml";
    }
    
    public Survey getCurrent()
    {
        return current;
    }
    
    public String saveRedirect()
    {      
        try {
            sc.edit(current);
            return "/survey/List.xhtml"; 
            
        } catch (RollbackFailureException ex) {
            return "Failed message"; 
        } catch (Exception ex) {
            return "Failed message"; 
        }      
    }
    
    public String redirectCreate()
    {
        Survey sur = new Survey();
        
        //add blank survey choices to allow the manager to enter answers
        List<SurveyChoice> blankAnswers = new ArrayList<SurveyChoice>();
        
        for(int choices = 0; choices < 4; choices++)
        {
            SurveyChoice sc = new SurveyChoice();
            blankAnswers.add(sc); 
            sc.setSurvey(sur); // may not work since the object has no id
        }
        
        //insert the blanks
        sur.setSurveyChoices(blankAnswers);
        this.current = sur; 
        //return "/survey/Edit.xhtml";
        return "/survey/Create.xhtml"; 
    }
    
    
    /**
     * This method is used to write the information to the database. 
     * @return      url of the page to redirect to. 
     */
    public String createSurvey()
    {
        
        //surveyChoices have no pk ids therefore 2 tractions require
        //rollback if error
               
        List<SurveyChoice> temp = current.getSurveyChoices();
        
        current.setSurveyChoices(new ArrayList<SurveyChoice>());
        
        try {
            sc.create(current);
            
            for(SurveyChoice scEle : temp)
            {
                scEle.setSurvey(current);
                surChoiceCon.create(scEle);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(SurveyBacking.class.getName()).log(Level.SEVERE, null, ex);
            em.getTransaction().rollback();
        }
        
        return "/survey/List.xhtml";
    }
    
    /*
            ------------ Method below used for 
    
    */
    
    
    public String addNewOption()
    {
        SurveyChoice sc = new SurveyChoice();     
        currentSC = sc;     
        return "/survey/surveyOptionForm.xhtml"; 
    }
    
    public String saveOption()
    {
        currentSC.setSurvey(current);
        current.getSurveyChoices().add(currentSC);
        return "/survey/Edit.xhtml";
    }
    
    public SurveyChoice getCurrentSC()
    {
        return currentSC;
    }
    
    
    //--- Delete 
    
    public String deleteItem(Integer id)
    {      
        try {        
            sc.destroy(id);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(SurveyBacking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SurveyBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/survey/List.xhtml";
    }
    
}
