/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.beans;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lynn
 */
@Entity
@Table(name = "artist_track")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArtistTrack.findAll", query = "SELECT a FROM ArtistTrack a")
    , @NamedQuery(name = "ArtistTrack.findById", query = "SELECT a FROM ArtistTrack a WHERE a.id = :id")})
public class ArtistTrack implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Artist artistId;
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Track trackId;

    public ArtistTrack() {
    }

    public ArtistTrack(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
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
        if (!(object instanceof ArtistTrack)) {
            return false;
        }
        ArtistTrack other = (ArtistTrack) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.ArtistTrack[ id=" + id + " ]";
    }
    
}
