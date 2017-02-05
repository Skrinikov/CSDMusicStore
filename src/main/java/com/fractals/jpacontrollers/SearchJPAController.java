package com.fractals.jpacontrollers;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author lynn
 */
@Named
@RequestScoped
public class SearchJPAController implements Serializable {
    
    @Resource
    private UserTransaction userTransaction;

    @PersistenceContext(unitName = "fractalsPU")
    private EntityManager entityManager;
    
    
    public List<Album> searchByAlbumTitle(String title) {
        if(title == null)
            throw new NullPointerException();
        
        List<Album> items = new ArrayList<>();      
        TypedQuery<Album> q = entityManager.createQuery("select a from Album a where a.title like %?1%'", Album.class);
        q.setParameter(1, title);
        items = (List<Album>)q.getResultList();      
        return items;
    }
    
    public List<Track> searchByTrackName(String title) {
        if(title == null)
            throw new NullPointerException();
        
        List<Track> items = new ArrayList<>();      
        TypedQuery<Track> q = entityManager.createQuery("select t from Track t where t.title like %?1%'", Track.class);
        q.setParameter(1, title);
        items = (List<Track>)q.getResultList();      
        return items;
    }
    
    public List<Track> searchByArtistName(String name) {
        if(name == null)
            throw new NullPointerException();

        //TODO
    }
    
    
}
