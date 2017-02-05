package com.fractals.jpacontrollers;

import com.fractals.beans.Album;
import com.fractals.beans.Track;
import com.fractals.utilities.Item;
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
    
    
    
    public List<Item> searchByAlbumTitle(String title) {
        if(title == null)
            throw new NullPointerException();
        
        List<Item> items = new ArrayList<>();
        
        Query q = entityManager.createQuery("select * from tracks where title like '%?1%' "
                                    + "union select * from albums where title like %?1%'");
        q.setParameter(1, title);
        items = (List<Item>)q.getResultList();
        
        return items;
    }
}
