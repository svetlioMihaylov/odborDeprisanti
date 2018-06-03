package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A WorkDetails.
 */
@Entity
@Table(name = "work_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "end_of_probation_date", nullable = false)
    private LocalDate endOfProbationDate;

    @Column(name = "salary_reevaluation_date")
    private Double salaryReevaluationDate;

    @Column(name = "contract_num")
    private Double contractNum;

    @Column(name = "resignation_request_ref_num")
    private Double resignationRequestRefNum;

    @Column(name = "resignation_order_ref_num")
    private Double resignationOrderRefNum;

    @Column(name = "year_vacation")
    private Integer yearVacation;

    @OneToOne
    @JoinColumn(unique = true)
    private EmployeePossition possition;

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

    public WorkDetails startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public WorkDetails endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndOfProbationDate() {
        return endOfProbationDate;
    }

    public WorkDetails endOfProbationDate(LocalDate endOfProbationDate) {
        this.endOfProbationDate = endOfProbationDate;
        return this;
    }

    public void setEndOfProbationDate(LocalDate endOfProbationDate) {
        this.endOfProbationDate = endOfProbationDate;
    }

    public Double getSalaryReevaluationDate() {
        return salaryReevaluationDate;
    }

    public WorkDetails salaryReevaluationDate(Double salaryReevaluationDate) {
        this.salaryReevaluationDate = salaryReevaluationDate;
        return this;
    }

    public void setSalaryReevaluationDate(Double salaryReevaluationDate) {
        this.salaryReevaluationDate = salaryReevaluationDate;
    }

    public Double getContractNum() {
        return contractNum;
    }

    public WorkDetails contractNum(Double contractNum) {
        this.contractNum = contractNum;
        return this;
    }

    public void setContractNum(Double contractNum) {
        this.contractNum = contractNum;
    }

    public Double getResignationRequestRefNum() {
        return resignationRequestRefNum;
    }

    public WorkDetails resignationRequestRefNum(Double resignationRequestRefNum) {
        this.resignationRequestRefNum = resignationRequestRefNum;
        return this;
    }

    public void setResignationRequestRefNum(Double resignationRequestRefNum) {
        this.resignationRequestRefNum = resignationRequestRefNum;
    }

    public Double getResignationOrderRefNum() {
        return resignationOrderRefNum;
    }

    public WorkDetails resignationOrderRefNum(Double resignationOrderRefNum) {
        this.resignationOrderRefNum = resignationOrderRefNum;
        return this;
    }

    public void setResignationOrderRefNum(Double resignationOrderRefNum) {
        this.resignationOrderRefNum = resignationOrderRefNum;
    }

    public Integer getYearVacation() {
        return yearVacation;
    }

    public WorkDetails yearVacation(Integer yearVacation) {
        this.yearVacation = yearVacation;
        return this;
    }

    public void setYearVacation(Integer yearVacation) {
        this.yearVacation = yearVacation;
    }

    public EmployeePossition getPossition() {
        return possition;
    }

    public WorkDetails possition(EmployeePossition employeePossition) {
        this.possition = employeePossition;
        return this;
    }

    public void setPossition(EmployeePossition employeePossition) {
        this.possition = employeePossition;
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
        WorkDetails workDetails = (WorkDetails) o;
        if (workDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkDetails{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", endOfProbationDate='" + getEndOfProbationDate() + "'" +
            ", salaryReevaluationDate=" + getSalaryReevaluationDate() +
            ", contractNum=" + getContractNum() +
            ", resignationRequestRefNum=" + getResignationRequestRefNum() +
            ", resignationOrderRefNum=" + getResignationOrderRefNum() +
            ", yearVacation=" + getYearVacation() +
            "}";
    }
}
