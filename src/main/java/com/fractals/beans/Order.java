/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lynn
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
    , @NamedQuery(name = "Order.findById", query = "SELECT o FROM Order o WHERE o.id = :id")
    , @NamedQuery(name = "Order.findByOrderDate", query = "SELECT o FROM Order o WHERE o.orderDate = :orderDate")
    , @NamedQuery(name = "Order.findByNetCost", query = "SELECT o FROM Order o WHERE o.netCost = :netCost")
    , @NamedQuery(name = "Order.findByPst", query = "SELECT o FROM Order o WHERE o.pst = :pst")
    , @NamedQuery(name = "Order.findByGst", query = "SELECT o FROM Order o WHERE o.gst = :gst")
    , @NamedQuery(name = "Order.findByHst", query = "SELECT o FROM Order o WHERE o.hst = :hst")
    , @NamedQuery(name = "Order.findByGrossCost", query = "SELECT o FROM Order o WHERE o.grossCost = :grossCost")})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "net_cost")
    private float netCost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pst")
    private float pst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gst")
    private float gst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hst")
    private float hst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gross_cost")
    private float grossCost;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public Order() {
    }

    public Order(Integer id) {
        this.id = id;
    }

    public Order(Integer id, Date orderDate, float netCost, float pst, float gst, float hst, float grossCost) {
        this.id = id;
        this.orderDate = orderDate;
        this.netCost = netCost;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
        this.grossCost = grossCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getNetCost() {
        return netCost;
    }

    public void setNetCost(float netCost) {
        this.netCost = netCost;
    }

    public float getPst() {
        return pst;
    }

    public void setPst(float pst) {
        this.pst = pst;
    }

    public float getGst() {
        return gst;
    }

    public void setGst(float gst) {
        this.gst = gst;
    }

    public float getHst() {
        return hst;
    }

    public void setHst(float hst) {
        this.hst = hst;
    }

    public float getGrossCost() {
        return grossCost;
    }

    public void setGrossCost(float grossCost) {
        this.grossCost = grossCost;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.Order[ id=" + id + " ]";
    }
    
}
