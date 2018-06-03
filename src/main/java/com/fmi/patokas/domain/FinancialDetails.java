package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FinancialDetails.
 */
@Entity
@Table(name = "financial_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FinancialDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "iban", nullable = false)
    private String iban;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bic")
    private String bic;

    @Column(name = "base_salary")
    private Double baseSalary;

    @Column(name = "gross_salary")
    private Double grossSalary;

    @Column(name = "sign_on_bonus")
    private Double signOnBonus;

    @Column(name = "annual_bonus")
    private Double annualBonus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public FinancialDetails iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBankName() {
        return bankName;
    }

    public FinancialDetails bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBic() {
        return bic;
    }

    public FinancialDetails bic(String bic) {
        this.bic = bic;
        return this;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public FinancialDetails baseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
        return this;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }

    public FinancialDetails grossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
        return this;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Double getSignOnBonus() {
        return signOnBonus;
    }

    public FinancialDetails signOnBonus(Double signOnBonus) {
        this.signOnBonus = signOnBonus;
        return this;
    }

    public void setSignOnBonus(Double signOnBonus) {
        this.signOnBonus = signOnBonus;
    }

    public Double getAnnualBonus() {
        return annualBonus;
    }

    public FinancialDetails annualBonus(Double annualBonus) {
        this.annualBonus = annualBonus;
        return this;
    }

    public void setAnnualBonus(Double annualBonus) {
        this.annualBonus = annualBonus;
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
        FinancialDetails financialDetails = (FinancialDetails) o;
        if (financialDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financialDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinancialDetails{" +
            "id=" + getId() +
            ", iban='" + getIban() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bic='" + getBic() + "'" +
            ", baseSalary=" + getBaseSalary() +
            ", grossSalary=" + getGrossSalary() +
            ", signOnBonus=" + getSignOnBonus() +
            ", annualBonus=" + getAnnualBonus() +
            "}";
    }
}
