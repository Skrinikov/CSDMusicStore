package com.fractals.backingbeans;

import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Province;
import com.fractals.controllers.OrderController;
import com.fractals.controllers.OrderItemJpaController;
import com.fractals.controllers.OrderJpaController;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class containing the methods and variables for managing Orders
 * @author MOUFFOK Sarah
 */
@Named("theOrders")
@SessionScoped
public class OrderBackingBean implements Serializable {

    @Inject
    private OrderJpaController orderJpaController;

    @Inject
    private OrderItemJpaController orderItemJpaController;
    
    @Inject
    private OrderController orderController;

    private Order selectedOrder;
    private OrderItem selectedOrderItem;

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order o) {
        selectedOrder = o;
    }

    public OrderItem getSelectedOrderItem() {
        return selectedOrderItem;
    }

    public void setSelectedOrderItem(OrderItem oi) {
        selectedOrderItem = oi;
    }
    
    public boolean isEmpty(){
        return orderJpaController.findOrderEntities().isEmpty();
    }

    /**
     * Deletes order item, + updates order price.
     * Error message if order item already deleted
     * @param o, the order item to delete
     * @throws Exception 
     */
    public void deleteOrderItem(OrderItem o) throws Exception {
        if (o.isCancelled()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    ResourceBundle.getBundle("Bundle").getString("order_item_already_deleted")));
        } else {
            o.setCancelled(true);
            orderItemJpaController.edit(o);
            Order order = changeNetCost(o.getOrder());
            orderJpaController.edit(order);
        }
    }

    /**
     * Delete order by deleting all the contained order items 
     * Error message if order already cancelled
     * @throws Exception 
     */
    public void deleteOrder() throws Exception {
        List<OrderItem> allItems = getOrderItemsOfOrder();
        
        if (selectedOrder.isCancelled()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    ResourceBundle.getBundle("Bundle").getString("order_already_deleted")));
        } else {
            for (OrderItem o : allItems) {
                o.setCancelled(true);
                orderItemJpaController.edit(o);
            }
            if (!allItems.isEmpty()) {
                Order order = changeNetCost(allItems.get(0).getOrder());
                orderJpaController.edit(order);
            }
        }          
    }
    /**
     * @return a list of all the existing orders
     */
    public List<Order> getOrders() {
        return orderJpaController.findOrderEntities();
    }
    
    /**
     * @return a list of all the order items of the selected order
     */
    public List<OrderItem> getOrderItemsOfOrder() {
       return selectedOrder.getOrderItems();
    }

    /**
     * Changes net and gross costs if an order or order items were cancelled.
     * @param order The order to modify.
     * @author Aline Shulzhenko
     */
    private Order changeNetCost(Order order) {
        double newNet = 0;
        for (OrderItem oi : order.getOrderItems()) {
            if (!oi.isCancelled()) {
                newNet += oi.getCost();
            }
        }
        order.setNetCost(newNet);
        Province province = order.getUser().getProvince();
        double newGross = newNet + newNet * province.getGst() + newNet * province.getHst() + newNet * province.getPst();
        order.setGrossCost(Math.round(newGross * 100000) / 100000.0);
        selectedOrder = order;
        return order;
    }
}
