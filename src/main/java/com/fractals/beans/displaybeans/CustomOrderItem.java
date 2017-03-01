package com.fractals.beans.displaybeans;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This beans holds information about a single order item and some more.
 * 
 * @author Danieil Skrinikov
 */
public class CustomOrderItem implements Serializable{
    
    private int id;
    private String title;
    private String type;
    private double cost;
    private double price;
    private LocalDateTime removed;
    
    /**
     * Default constructor.
     */
    public CustomOrderItem(){
        id = 0;
        title = "";
        type = "";
        cost = 0.;
        price = 0.;
        removed = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getRemoved() {
        return removed;
    }

    public void setRemoved(LocalDateTime removed) {
        this.removed = removed;
    }
    
    
}
