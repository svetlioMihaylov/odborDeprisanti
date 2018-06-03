package com.fmi.patokas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ExternalPerson.
 */
@Entity
@Table(name = "external_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "first_name_native", nullable = false)
    private String firstNameNative;

    @NotNull
    @Column(name = "middle_name_native", nullable = false)
    private String middleNameNative;

    @NotNull
    @Column(name = "last_name_native", nullable = false)
    private String lastNameNative;

    @OneToMany(mappedBy = "benefits")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Benefit> owners = new HashSet<>();

    @ManyToOne
    private Employee owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public ExternalPerson firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ExternalPerson middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public ExternalPerson lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNameNative() {
        return firstNameNative;
    }

    public ExternalPerson firstNameNative(String firstNameNative) {
        this.firstNameNative = firstNameNative;
        return this;
    }

    public void setFirstNameNative(String firstNameNative) {
        this.firstNameNative = firstNameNative;
    }

    public String getMiddleNameNative() {
        return middleNameNative;
    }

    public ExternalPerson middleNameNative(String middleNameNative) {
        this.middleNameNative = middleNameNative;
        return this;
    }

    public void setMiddleNameNative(String middleNameNative) {
        this.middleNameNative = middleNameNative;
    }

    public String getLastNameNative() {
        return lastNameNative;
    }

    public ExternalPerson lastNameNative(String lastNameNative) {
        this.lastNameNative = lastNameNative;
        return this;
    }

    public void setLastNameNative(String lastNameNative) {
        this.lastNameNative = lastNameNative;
    }

    public Set<Benefit> getOwners() {
        return owners;
    }

    public ExternalPerson owners(Set<Benefit> benefits) {
        this.owners = benefits;
        return this;
    }

    public ExternalPerson addOwner(Benefit benefit) {
        this.owners.add(benefit);
        benefit.setBenefits(this);
        return this;
    }

    public ExternalPerson removeOwner(Benefit benefit) {
        this.owners.remove(benefit);
        benefit.setBenefits(null);
        return this;
    }

    public void setOwners(Set<Benefit> benefits) {
        this.owners = benefits;
    }

    public Employee getOwner() {
        return owner;
    }

    public ExternalPerson owner(Employee employee) {
        this.owner = employee;
        return this;
    }

    public void setOwner(Employee employee) {
        this.owner = employee;
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
        ExternalPerson externalPerson = (ExternalPerson) o;
        if (externalPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), externalPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExternalPerson{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstNameNative='" + getFirstNameNative() + "'" +
            ", middleNameNative='" + getMiddleNameNative() + "'" +
            ", lastNameNative='" + getLastNameNative() + "'" +
            "}";
    }
}
