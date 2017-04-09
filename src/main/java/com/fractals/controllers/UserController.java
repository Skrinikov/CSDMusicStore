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
 * @author MOUFFOK Sarah, Aline Shulzhenko
 */
@Named
@RequestScoped
public class UserController implements Serializable {

    @PersistenceContext
    private EntityManager em;

    /**
     * Gets the net cost of all the orders made by a user
     * @param user, the user
     * @return the total net cost paid by the user
     */
    public Number getTotalSalesByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        Root<Order> root = query.from(Order.class);
        query.select(cb.sum(root.get(Order_.netCost)));
        query.where(cb.equal(root.get(Order_.user), user));
        Number result = em.createQuery(query).getSingleResult();
        return result == null ? 0 : result;
    }
    
    /**
     * Searches users table by the username.
     * @param username The username to search by in the users table.
     * @return the user with the corresponding username.
     * @author Aline Shulzhenko
     */
    public User getByUsername(String username) {
        return (User)em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
    }
    
    /**
     * Refreshes the user to the database state.
     * @param user The user to refresh.
     * @author Aline Shulzhenko
     */
    public void refreshUser(User user) {
        em.refresh(user);
    }
    
}
