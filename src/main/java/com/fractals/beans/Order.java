package com.fractals.beans;

import com.fractals.beanvalidators.PastLocalDateTime;
import com.fractals.beanvalidators.PositiveDouble;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

/**
 * Class corresponding to the orders table.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
@Entity
@Table(name = "orders")
@Converter(name="datetimeConverter", converterClass=com.fractals.converters.LocalDateTimeAttributeConverter.class)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
    , @NamedQuery(name = "Order.findById", query = "SELECT o FROM Order o WHERE o.id = :id")
    , @NamedQuery(name = "Order.findByOrderDate", query = "SELECT o FROM Order o WHERE o.orderDate = :orderDate")
    , @NamedQuery(name = "Order.findByNetCost", query = "SELECT o FROM Order o WHERE o.netCost = :netCost")
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
    @PastLocalDateTime
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Convert("datetimeConverter")
    private LocalDateTime orderDate;
    
    @Basic(optional = false)
    @NotNull
    @PositiveDouble
    @Column(name = "net_cost")
    private double netCost;
    
    @Basic(optional = false)
    @NotNull
    @PositiveDouble
    @Column(name = "gross_cost")
    private double grossCost;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(Integer id) {
        this.id = id;
    }

    public Order(Integer id, LocalDateTime orderDate, double netCost, double grossCost) {
        this.id = id;
        this.orderDate = orderDate;
        this.netCost = netCost;
        this.grossCost = grossCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getNetCost() {
        return netCost;
    }

    public void setNetCost(double netCost) {
        this.netCost = netCost;
    }

    public double getGrossCost() {
        return grossCost;
    }

    public void setGrossCost(double grossCost) {
        this.grossCost = grossCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
    
    @XmlTransient
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItemsCollection) {
        this.orderItems = orderItemsCollection;
    }
    
}
