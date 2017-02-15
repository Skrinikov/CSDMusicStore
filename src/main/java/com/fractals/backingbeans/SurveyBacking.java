
package com.fractals.backingbeans;

import com.fractals.beans.Survey;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
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
    
    
    
    
}
