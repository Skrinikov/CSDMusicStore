package com.fractals.beans;

import com.fractals.beanvalidators.EmailCheck;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class corresponding to the users table.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
    , @NamedQuery(name = "User.findByTitle", query = "SELECT u FROM User u WHERE u.title = :title")
    , @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "User.findByCompany", query = "SELECT u FROM User u WHERE u.company = :company")
    , @NamedQuery(name = "User.findByAddress1", query = "SELECT u FROM User u WHERE u.address1 = :address1")
    , @NamedQuery(name = "User.findByAddress2", query = "SELECT u FROM User u WHERE u.address2 = :address2")
    , @NamedQuery(name = "User.findByCity", query = "SELECT u FROM User u WHERE u.city = :city")
    , @NamedQuery(name = "User.findByProvince", query = "SELECT u FROM User u WHERE u.province = :province")
    , @NamedQuery(name = "User.findByCountry", query = "SELECT u FROM User u WHERE u.country = :country")
    , @NamedQuery(name = "User.findByPostalCode", query = "SELECT u FROM User u WHERE u.postalCode = :postalCode")
    , @NamedQuery(name = "User.findByHomeTel", query = "SELECT u FROM User u WHERE u.homeTel = :homeTel")
    , @NamedQuery(name = "User.findByCellTel", query = "SELECT u FROM User u WHERE u.cellTel = :cellTel")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByLastGenre", query = "SELECT u FROM User u WHERE u.lastGenre = :lastGenre")
    , @NamedQuery(name = "User.findByIsAdmin", query = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @NotNull
    private Province province;
    
    @JoinColumn(name = "last_genre", referencedColumnName = "id")
    @ManyToOne
    private Genre lastGenre;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "password")
    private byte[] password;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username", unique=true)
    private String username;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "salt")
    private String salt;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title")
    private String title;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "first_name")
    private String firstName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "last_name")
    private String lastName;
    
    @Size(max = 50)
    @Column(name = "company")
    private String company;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "address_1")
    private String address1;
    
    @Size(max = 50)
    @Column(name = "address_2")
    private String address2;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "city")
    private String city;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "country")
    private String country;
    
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="^[A-Za-z][0-9][A-Za-z][ ]?[0-9][A-Za-z][0-9]$")
    @Size(min = 1, max = 50)
    @Column(name = "postal_code")
    private String postalCode;
    
    @Size(max = 20)
    @Pattern(regexp="(^$)|(^\\(?([0-9]{3})\\)?(\\s*)[-. ]?(\\s*)([0-9]{3})(\\s*)[-. ]?(\\s*)([0-9]{4})$)")
    @Column(name = "home_tel")
    private String homeTel;
    
    @Size(max = 20)
    @Pattern(regexp="(^$)|(^\\(?([0-9]{3})\\)?(\\s*)[-. ]?(\\s*)([0-9]{3})(\\s*)[-. ]?(\\s*)([0-9]{4})$)")
    @Column(name = "cell_tel")
    private String cellTel;
    
    @Basic(optional = false)
    @NotNull
    @EmailCheck
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @NotNull
    @Column(name = "is_admin")
    private boolean isAdmin;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Review> reviews;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String username, String salt, String title, String firstName, String lastName, String address1, String city, String country, String postalCode, String email, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.salt = salt;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    public String getCellTel() {
        return cellTel;
    }

    public void setCellTel(String cellTel) {
        this.cellTel = cellTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @XmlTransient
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @XmlTransient
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.User[ id=" + id + " ] "+username;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Genre getLastGenre() {
        return lastGenre;
    }

    public void setLastGenre(Genre lastGenre) {
        this.lastGenre = lastGenre;
    }
    
}
