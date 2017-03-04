/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.controllers.OrderItemJpaController;
import com.fractals.controllers.OrderJpaControllerS;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Sarah
 */
@Named("theOrders")
@SessionScoped
public class OrderBackingBean implements Serializable {
    
    private Order selectedOrder;
    public Order getSelectedOrder(){ return selectedOrder;}
    public void setSelectedOrder(Order o){ selectedOrder = o;}
    
    private OrderItem selectedOrderItem;
    public OrderItem getSelectedOrderItem(){ return selectedOrderItem;}
    public void setSelectedOrderItem(OrderItem oi){ selectedOrderItem = oi;}
    
    @Inject
    private OrderJpaControllerS orderJpaController;
    
    @Inject
    private OrderItemJpaController orderItemJpaController;
    
    public List<Order> getOrders(){
        return orderJpaController.findOrderEntities();
    }
    
    public List<OrderItem> getOrderItemsOfOrder(){
        CriteriaBuilder cb = orderItemJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<OrderItem> query = cb.createQuery(OrderItem.class);
        Root<OrderItem> root = query.from(OrderItem.class);
        query.select(root);
        query.where(cb.equal(root.get("order"), selectedOrder));
        return orderJpaController.getEntityManager().createQuery(query).getResultList();
    }
    
    public void removeOrder() throws Exception {
        orderJpaController.destroy(selectedOrder.getId());
    }
    
    public String returnToPage(){
        selectedOrder = null;
        selectedOrderItem = null;
        return "/management/order/ordersList.xhtml";
    }
}