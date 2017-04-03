package com.fractals.controllers;

import com.fractals.beans.Order;
import com.fractals.beans.Order_;
import com.fractals.beans.Review;
import com.fractals.beans.Review_;
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
    
    public Number getNumberOfReviews(User u){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        Root<Review> root = query.from(Review.class);
        query.select(cb.count(root.get(Review_.id)));
        query.where(cb.equal(root.get(Review_.user), u));
        Number result = em.createQuery(query).getSingleResult();
        return result == null ? 0 : result;
    }
}
