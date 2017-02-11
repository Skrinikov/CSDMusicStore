package com.fractals.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

/**
 *
 * @author lynn
 */
@Entity
@Table(name = "tracks")
@Converter(name="datetimeConverter", converterClass=com.fractals.beans.LocalDateTimeAttributeConverter.class)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Track.findAll", query = "SELECT t FROM Track t")
    , @NamedQuery(name = "Track.findById", query = "SELECT t FROM Track t WHERE t.id = :id")
    , @NamedQuery(name = "Track.findByTitle", query = "SELECT t FROM Track t WHERE t.title = :title")
    , @NamedQuery(name = "Track.findBySongwriter", query = "SELECT t FROM Track t WHERE t.songwriter = :songwriter")
    , @NamedQuery(name = "Track.findByDuration", query = "SELECT t FROM Track t WHERE t.duration = :duration")
    , @NamedQuery(name = "Track.findByAlbumNum", query = "SELECT t FROM Track t WHERE t.albumNum = :albumNum")
    , @NamedQuery(name = "Track.findByCoverFile", query = "SELECT t FROM Track t WHERE t.coverFile = :coverFile")
    , @NamedQuery(name = "Track.findByCreatedAt", query = "SELECT t FROM Track t WHERE t.createdAt = :createdAt")
    , @NamedQuery(name = "Track.findByCostPrice", query = "SELECT t FROM Track t WHERE t.costPrice = :costPrice")
    , @NamedQuery(name = "Track.findByListPrice", query = "SELECT t FROM Track t WHERE t.listPrice = :listPrice")
    , @NamedQuery(name = "Track.findBySalePrice", query = "SELECT t FROM Track t WHERE t.salePrice = :salePrice")
    , @NamedQuery(name = "Track.findByRemovedAt", query = "SELECT t FROM Track t WHERE t.removedAt = :removedAt")
    , @NamedQuery(name = "Track.findByAvailable", query = "SELECT t FROM Track t WHERE t.available = :available")
    , @NamedQuery(name = "Track.findByIsIndividual", query = "SELECT t FROM Track t WHERE t.isIndividual = :isIndividual")})
public class Track implements Serializable {

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
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Convert("datetimeConverter")
    private LocalDateTime createdAt;
    
    
    @Column(name = "removed_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Convert("datetimeConverter")
    private LocalDateTime removedAt;
    
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
    private Album album;
    
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Genre genre;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "track")
    private List<Review> reviews;
    
    @ManyToMany(mappedBy = "tracks")
    private List<Artist> artists;
    
    @OneToMany(mappedBy = "track")
    private List<OrderItem> orderItems;

    public Track() {
    }

    public Track(Integer id) {
        this.id = id;
    }

    public Track(Integer id, String title, String songwriter, String duration, int albumNum, String coverFile, LocalDateTime createdAt, double costPrice, double listPrice, double salePrice, boolean available, boolean isIndividual) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
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

    public LocalDateTime getRemovedAt() {
        return removedAt;
    }

    public void setRemovedAt(LocalDateTime removedAt) {
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @XmlTransient
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @XmlTransient
    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @XmlTransient
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
        return "com.fractals.beans.Track[ id=" + id + " ]";
    }
    
}
