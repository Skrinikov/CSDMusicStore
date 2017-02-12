package com.fractals.beans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lynn
 */
@Entity
@Table(name = "provinces")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Province.findAll", query = "SELECT p FROM Province p")
    , @NamedQuery(name = "Province.findById", query = "SELECT p FROM Province p WHERE p.id = :id")
    , @NamedQuery(name = "Province.findByName", query = "SELECT p FROM Province p WHERE p.name = :name")
    , @NamedQuery(name = "Province.findByPst", query = "SELECT p FROM Province p WHERE p.pst = :pst")
    , @NamedQuery(name = "Province.findByGst", query = "SELECT p FROM Province p WHERE p.gst = :gst")
    , @NamedQuery(name = "Province.findByHst", query = "SELECT p FROM Province p WHERE p.hst = :hst")})
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "pst")
    private double pst;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "gst")
    private double gst;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "hst")
    private double hst;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "province")
    private List<User> users;

    public Province() {
    }

    public Province(Integer id) {
        this.id = id;
    }

    public Province(Integer id, String name, double pst, double gst, double hst) {
        this.id = id;
        this.name = name;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPst() {
        return pst;
    }

    public void setPst(double pst) {
        this.pst = pst;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getHst() {
        return hst;
    }

    public void setHst(double hst) {
        this.hst = hst;
    }

    @XmlTransient
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> userCollection) {
        this.users = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Province)) {
            return false;
        }
        Province other = (Province) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
