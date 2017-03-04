package com.fractals.beans;

import com.fractals.beanvalidators.PositiveDouble;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class corresponding to the order_items table.
 *
 * @author Aline Shulzhenko
 * @version 02/03/2017
 * @since 1.8
 */
@Entity
@Table(name = "order_items")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderItem.findAll", query = "SELECT o FROM OrderItem o")
    , @NamedQuery(name = "OrderItem.findById", query = "SELECT o FROM OrderItem o WHERE o.id = :id")
    , @NamedQuery(name = "OrderItem.findByOrderId", query = "SELECT o FROM OrderItem o WHERE o.order = :order")
    , @NamedQuery(name = "OrderItem.findByCost", query = "SELECT o FROM OrderItem o WHERE o.cost = :cost")})
public class OrderItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @PositiveDouble
    @Column(name = "cost")
    private double cost;   
    
    @NotNull
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne
    private Order order;
        
    @Basic(optional = false)
    @NotNull
    @Column(name = "cancelled")
    private boolean cancelled;
    
    
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    @ManyToOne
    private Album album;
    
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    @ManyToOne
    private Track track;

    public OrderItem() {
    }

    public OrderItem(Integer id) {
        this.id = id;
    }

    public OrderItem(Integer id, Order order, double cost) {
        this.id = id;
        this.order = order;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrderItem)) {
            return false;
        }
        OrderItem other = (OrderItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.OrderItem[ id=" + id + " ]";
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
}
