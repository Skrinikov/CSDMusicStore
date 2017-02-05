/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.beans;

import com.fractals.utilities.Item;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "tracks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tracks.findAll", query = "SELECT t FROM Tracks t")
    , @NamedQuery(name = "Tracks.findById", query = "SELECT t FROM Tracks t WHERE t.id = :id")
    , @NamedQuery(name = "Tracks.findByTitle", query = "SELECT t FROM Tracks t WHERE t.title = :title")
    , @NamedQuery(name = "Tracks.findBySongwriter", query = "SELECT t FROM Tracks t WHERE t.songwriter = :songwriter")
    , @NamedQuery(name = "Tracks.findByDuration", query = "SELECT t FROM Tracks t WHERE t.duration = :duration")
    , @NamedQuery(name = "Tracks.findByAlbumNum", query = "SELECT t FROM Tracks t WHERE t.albumNum = :albumNum")
    , @NamedQuery(name = "Tracks.findByCoverFile", query = "SELECT t FROM Tracks t WHERE t.coverFile = :coverFile")
    , @NamedQuery(name = "Tracks.findByCreatedAt", query = "SELECT t FROM Tracks t WHERE t.createdAt = :createdAt")
    , @NamedQuery(name = "Tracks.findByCostPrice", query = "SELECT t FROM Tracks t WHERE t.costPrice = :costPrice")
    , @NamedQuery(name = "Tracks.findByListPrice", query = "SELECT t FROM Tracks t WHERE t.listPrice = :listPrice")
    , @NamedQuery(name = "Tracks.findBySalePrice", query = "SELECT t FROM Tracks t WHERE t.salePrice = :salePrice")
    , @NamedQuery(name = "Tracks.findByRemovedAt", query = "SELECT t FROM Tracks t WHERE t.removedAt = :removedAt")
    , @NamedQuery(name = "Tracks.findByAvailable", query = "SELECT t FROM Tracks t WHERE t.available = :available")
    , @NamedQuery(name = "Tracks.findByIsIndividual", query = "SELECT t FROM Tracks t WHERE t.isIndividual = :isIndividual")})
public class Track extends Item implements Serializable {

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
    @Size(min = 1, max = 255)
    @Column(name = "songwriter")
    private String songwriter;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "duration")
    private String duration;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "album_num")
    private int albumNum;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cover_file")
    private String coverFile;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost_price")
    private float costPrice;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "list_price")
    private float listPrice;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "sale_price")
    private float salePrice;
    
    @Column(name = "removed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date removedAt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "available")
    private boolean available;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_individual")
    private boolean isIndividual;
    
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Album albumId;
    
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Genre genreId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trackId")
    private Collection<Review> reviews;
    
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "trackId")
    private Collection<ArtistTrack> artistTrackCollection;*/
    @ManyToMany(mappedBy = "tracks")
    private Collection<Artist> artists;
    
    @OneToMany(mappedBy = "trackId")
    private Collection<OrderItem> orderItems;

    public Track() {
    }

    public Track(Integer id) {
        this.id = id;
    }

    public Track(Integer id, String title, String songwriter, String duration, int albumNum, String coverFile, Date createdAt, float costPrice, float listPrice, float salePrice, boolean available, boolean isIndividual) {
        this.id = id;
        this.title = title;
        this.songwriter = songwriter;
        this.duration = duration;
        this.albumNum = albumNum;
        this.coverFile = coverFile;
        this.createdAt = createdAt;
        this.costPrice = costPrice;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.available = available;
        this.isIndividual = isIndividual;
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

    public String getSongwriter() {
        return songwriter;
    }

    public void setSongwriter(String songwriter) {
        this.songwriter = songwriter;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getAlbumNum() {
        return albumNum;
    }

    public void setAlbumNum(int albumNum) {
        this.albumNum = albumNum;
    }

    public String getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(String coverFile) {
        this.coverFile = coverFile;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public float getListPrice() {
        return listPrice;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
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

    public boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(boolean isIndividual) {
        this.isIndividual = isIndividual;
    }

    public Album getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Album albumId) {
        this.albumId = albumId;
    }

    public Genre getGenreId() {
        return genreId;
    }

    public void setGenreId(Genre genreId) {
        this.genreId = genreId;
    }

    @XmlTransient
    public Collection<Review> getReviewsCollection() {
        return reviews;
    }

    public void setReviewsCollection(Collection<Review> reviewsCollection) {
        this.reviews = reviewsCollection;
    }

    @XmlTransient
    public Collection<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Collection<Artist> artists) {
        this.artists = artists;
    }

    @XmlTransient
    public Collection<OrderItem> getOrderItemsCollection() {
        return orderItems;
    }

    public void setOrderItemsCollection(Collection<OrderItem> orderItemsCollection) {
        this.orderItems = orderItemsCollection;
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
        if (!(object instanceof Track)) {
            return false;
        }
        Track other = (Track) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.Tracks[ id=" + id + " ]";
    }
    
}
