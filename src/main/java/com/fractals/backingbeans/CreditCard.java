package com.fractals.backingbeans;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * The class that is responsible for Credit Card and validating its information.
 *
 * @author Aline Shulzhenko
 * @version 25/02/2017
 * @since 1.8
 */
@Named("credit")
@SessionScoped
public class CreditCard  implements Serializable {
    private String brand;
    private String number;
    private Date expirationDate;
    private String name;
    private Integer code;

    /**
     * Returns the secret code for Visa credit card.
     * @return the secret code for Visa credit card.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Sets the secret code for Visa credit card.
     * @param code the secret code for Visa credit card.
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Returns the brand of the credit card.
     * @return the brand of the credit card.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the credit card.
     * @param brand the brand of the credit card.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns credit card number.
     * @return credit card number.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets credit card number.
     * @param number credit card number.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Returns the expiration date of the credit card.
     * @return the expiration date of the credit card.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the credit card.
     * @param expirationDate the expiration date of the credit card.
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Returns cardholder's name.
     * @return cardholder's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets cardholder's name.
     * @param name cardholder's name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
