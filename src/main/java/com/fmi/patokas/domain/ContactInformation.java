package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ContactInformation.
 */
@Entity
@Table(name = "contact_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "permanent_address", nullable = false)
    private String permanentAddress;

    @NotNull
    @Column(name = "current_address", nullable = false)
    private String currentAddress;

    @Column(name = "personal_mail")
    private String personalMail;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public ContactInformation permanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
        return this;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public ContactInformation currentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
        return this;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPersonalMail() {
        return personalMail;
    }

    public ContactInformation personalMail(String personalMail) {
        this.personalMail = personalMail;
        return this;
    }

    public void setPersonalMail(String personalMail) {
        this.personalMail = personalMail;
    }

    public String getPhone() {
        return phone;
    }

    public ContactInformation phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        ContactInformation contactInformation = (ContactInformation) o;
        if (contactInformation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactInformation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactInformation{" +
            "id=" + getId() +
            ", permanentAddress='" + getPermanentAddress() + "'" +
            ", currentAddress='" + getCurrentAddress() + "'" +
            ", personalMail='" + getPersonalMail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
