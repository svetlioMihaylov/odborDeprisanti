package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fmi.patokas.domain.enumeration.BenefitType;

/**
 * A Benefit.
 */
@Entity
@Table(name = "benefit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Benefit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "benefit_type")
    private BenefitType benefitType;

    @ManyToOne
    private Employee employeeOwner;

    @ManyToOne
    private ExternalPerson externalPersonOwner;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private ExternalPerson benefits;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Benefit startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Benefit endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BenefitType getBenefitType() {
        return benefitType;
    }

    public Benefit benefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
        return this;
    }

    public void setBenefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
    }

    public Employee getEmployeeOwner() {
        return employeeOwner;
    }

    public Benefit employeeOwner(Employee employee) {
        this.employeeOwner = employee;
        return this;
    }

    public void setEmployeeOwner(Employee employee) {
        this.employeeOwner = employee;
    }

    public ExternalPerson getExternalPersonOwner() {
        return externalPersonOwner;
    }

    public Benefit externalPersonOwner(ExternalPerson externalPerson) {
        this.externalPersonOwner = externalPerson;
        return this;
    }

    public void setExternalPersonOwner(ExternalPerson externalPerson) {
        this.externalPersonOwner = externalPerson;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Benefit employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ExternalPerson getBenefits() {
        return benefits;
    }

    public Benefit benefits(ExternalPerson externalPerson) {
        this.benefits = externalPerson;
        return this;
    }

    public void setBenefits(ExternalPerson externalPerson) {
        this.benefits = externalPerson;
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
        Benefit benefit = (Benefit) o;
        if (benefit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), benefit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Benefit{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", benefitType='" + getBenefitType() + "'" +
            "}";
    }
}
