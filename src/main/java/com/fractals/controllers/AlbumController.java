package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.OrderItem_;
import com.fractals.beans.Order_;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named
@RequestScoped
public class AlbumController implements Serializable {
   
    @PersistenceContext
    private EntityManager em;
             
    public Number getTotalSales(Album a){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        Root<OrderItem> root = query.from(OrderItem.class);
        Join<OrderItem,Order> order = root.join(OrderItem_.order);
        query.select(cb.sum(order.get(Order_.netCost)));
        query.where(cb.equal(root.get(OrderItem_.album), a));
        Number result = em.createQuery(query).getSingleResult();      
        return result == null ? 0 : result;
    }
   
}
