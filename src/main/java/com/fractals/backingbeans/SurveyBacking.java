
package com.fractals.backingbeans;

import com.fractals.beans.Survey;
import com.fractals.controllers.SurveyJpaController;
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
    
    @Inject
    private SurveyJpaController sc; 
     
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
        
        this.current = sur; 
        
        return "/survey/Create.xhtml"; 
    }
    
    
    
}
