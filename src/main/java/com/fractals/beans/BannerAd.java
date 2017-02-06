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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lynn
 */
@Entity
@Table(name = "banner_ads")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BannerAd.findAll", query = "SELECT b FROM BannerAd b")
    , @NamedQuery(name = "BannerAd.findById", query = "SELECT b FROM BannerAd b WHERE b.id = :id")
    , @NamedQuery(name = "BannerAd.findByLink", query = "SELECT b FROM BannerAd b WHERE b.link = :link")
    , @NamedQuery(name = "BannerAd.findBySource", query = "SELECT b FROM BannerAd b WHERE b.source = :source")
    , @NamedQuery(name = "BannerAd.findByVisible", query = "SELECT b FROM BannerAd b WHERE b.visible = :visible")})
public class BannerAd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "link")
    private String link;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "source")
    private String source;
    @Basic(optional = false)
    @NotNull
    @Column(name = "visible")
    private boolean visible;

    public BannerAd() {
    }

    public BannerAd(Integer id) {
        this.id = id;
    }

    public BannerAd(Integer id, String link, String source, boolean visible) {
        this.id = id;
        this.link = link;
        this.source = source;
        this.visible = visible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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
        if (!(object instanceof BannerAd)) {
            return false;
        }
        BannerAd other = (BannerAd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.BannerAd[ id=" + id + " ]";
    }
    
}
