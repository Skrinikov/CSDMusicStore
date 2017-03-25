package com.fractals.controllers;

import com.fractals.beans.Order;
import com.fractals.beans.Order_;
import com.fractals.beans.User;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named
@RequestScoped
public class UserController implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public Number getTotalSalesByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        Root<Order> root = query.from(Order.class);
        query.select(cb.sum(root.get(Order_.netCost)));
        query.where(cb.equal(root.get(Order_.user), user));
        Number result = em.createQuery(query).getSingleResult();
        return result == null ? 0 : result;
    }

}
