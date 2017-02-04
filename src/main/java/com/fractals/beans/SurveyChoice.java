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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lynn
 */
@Entity
@Table(name = "survey_choices")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SurveyChoices.findAll", query = "SELECT s FROM SurveyChoices s")
    , @NamedQuery(name = "SurveyChoices.findById", query = "SELECT s FROM SurveyChoices s WHERE s.id = :id")
    , @NamedQuery(name = "SurveyChoices.findByChoice", query = "SELECT s FROM SurveyChoices s WHERE s.choice = :choice")
    , @NamedQuery(name = "SurveyChoices.findByNumVotes", query = "SELECT s FROM SurveyChoices s WHERE s.numVotes = :numVotes")})
public class SurveyChoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "choice")
    private String choice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_votes")
    private int numVotes;
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @ManyToOne
    private Survey surveyId;

    public SurveyChoice() {
    }

    public SurveyChoice(Integer id) {
        this.id = id;
    }

    public SurveyChoice(Integer id, String choice, int numVotes) {
        this.id = id;
        this.choice = choice;
        this.numVotes = numVotes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
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
        if (!(object instanceof SurveyChoice)) {
            return false;
        }
        SurveyChoice other = (SurveyChoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fractals.beans.SurveyChoices[ id=" + id + " ]";
    }
    
}
