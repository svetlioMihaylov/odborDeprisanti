package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A IDCard.
 */
@Entity
@Table(name = "id_card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IDCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_number", nullable = false)
    private String idNumber;

    @NotNull
    @Column(name = "date_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @NotNull
    @Column(name = "date_of_expiry", nullable = false)
    private LocalDate dateOfExpiry;

    @NotNull
    @Column(name = "issued_by", nullable = false)
    private String issuedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public IDCard idNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public IDCard dateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getDateOfExpiry() {
        return dateOfExpiry;
    }

    public IDCard dateOfExpiry(LocalDate dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
        return this;
    }

    public void setDateOfExpiry(LocalDate dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public IDCard issuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
        return this;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IDCard iDCard = (IDCard) o;
        if (iDCard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iDCard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IDCard{" +
            "id=" + getId() +
            ", idNumber='" + getIdNumber() + "'" +
            ", dateOfIssue='" + getDateOfIssue() + "'" +
            ", dateOfExpiry='" + getDateOfExpiry() + "'" +
            ", issuedBy='" + getIssuedBy() + "'" +
            "}";
    }
}
