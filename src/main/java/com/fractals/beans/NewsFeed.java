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
@Table(name = "news_feeds")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NewsFeed.findAll", query = "SELECT n FROM NewsFeed n")
    , @NamedQuery(name = "NewsFeed.findById", query = "SELECT n FROM NewsFeed n WHERE n.id = :id")
    , @NamedQuery(name = "NewsFeed.findByLink", query = "SELECT n FROM NewsFeed n WHERE n.link = :link")
    , @NamedQuery(name = "NewsFeed.findBySource", query = "SELECT n FROM NewsFeed n WHERE n.source = :source")
    , @NamedQuery(name = "NewsFeed.findByVisible", query = "SELECT n FROM NewsFeed n WHERE n.visible = :visible")})
public class NewsFeed implements Serializable {

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

    public NewsFeed() {
    }

    public NewsFeed(Integer id) {
        this.id = id;
    }

    public NewsFeed(Integer id, String link, String source, boolean visible) {
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
        if (!(object instanceof NewsFeed)) {
            return false;
        }
        NewsFeed other = (NewsFeed) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.NewsFeed[ id=" + id + " ]";
    }
    
}
