/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lynn
 */
@Entity
@Table(name = "albums")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
    , @NamedQuery(name = "Album.findById", query = "SELECT a FROM Album a WHERE a.id = :id")
    , @NamedQuery(name = "Album.findByTitle", query = "SELECT a FROM Album a WHERE a.title = :title")
    , @NamedQuery(name = "Album.findByReleaseDate", query = "SELECT a FROM Album a WHERE a.releaseDate = :releaseDate")
    , @NamedQuery(name = "Album.findByRecordLabel", query = "SELECT a FROM Album a WHERE a.recordLabel = :recordLabel")
    , @NamedQuery(name = "Album.findByNumTracks", query = "SELECT a FROM Album a WHERE a.numTracks = :numTracks")
    , @NamedQuery(name = "Album.findByCreatedAt", query = "SELECT a FROM Album a WHERE a.createdAt = :createdAt")
    , @NamedQuery(name = "Album.findByCostPrice", query = "SELECT a FROM Album a WHERE a.costPrice = :costPrice")
    , @NamedQuery(name = "Album.findByListPrice", query = "SELECT a FROM Album a WHERE a.listPrice = :listPrice")
    , @NamedQuery(name = "Album.findBySalePrice", query = "SELECT a FROM Album a WHERE a.salePrice = :salePrice")
    , @NamedQuery(name = "Album.findByRemovedAt", query = "SELECT a FROM Album a WHERE a.removedAt = :removedAt")
    , @NamedQuery(name = "Album.findByAvailable", query = "SELECT a FROM Album a WHERE a.available = :available")})
@Named("album")
@RequestScoped
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "record_label")
    private String recordLabel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_tracks")
    private int numTracks;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost_price")
    private double costPrice;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "list_price")
    private double listPrice;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "sale_price")
    private double salePrice;
    
    @Column(name = "removed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date removedAt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "available")
    private boolean available;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albumId")
    private Collection<Track> tracksCollection;
    
    @OneToMany(mappedBy = "albumId")
    private Collection<OrderItem> orderItemsCollection;
    
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Artist artist;

    public Album() {
    }

    public Album(Integer id) {
        this.id = id;
    }

    public Album(Integer id, String title, Date releaseDate, String recordLabel, int numTracks, Date createdAt, double costPrice, double listPrice, double salePrice, boolean available) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.recordLabel = recordLabel;
        this.numTracks = numTracks;
        this.createdAt = createdAt;
        this.costPrice = costPrice;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRecordLabel() {
        return recordLabel;
    }

    public void setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
    }

    public int getNumTracks() {
        return numTracks;
    }

    public void setNumTracks(int numTracks) {
        this.numTracks = numTracks;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public Date getRemovedAt() {
        return removedAt;
    }

    public void setRemovedAt(Date removedAt) {
        this.removedAt = removedAt;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @XmlTransient
    public Collection<Track> getTracksCollection() {
        return tracksCollection;
    }

    public void setTracksCollection(Collection<Track> tracksCollection) {
        this.tracksCollection = tracksCollection;
    }

    @XmlTransient
    public Collection<OrderItem> getOrderItemsCollection() {
        return orderItemsCollection;
    }

    public void setOrderItemsCollection(Collection<OrderItem> orderItemsCollection) {
        this.orderItemsCollection = orderItemsCollection;
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
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.Album[ id=" + id + " ]";
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artistId) {
        this.artist = artistId;
    }
    
}
