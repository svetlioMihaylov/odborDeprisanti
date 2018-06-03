package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VacationRequests.
 */
@Entity
@Table(name = "vacation_requests")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VacationRequests implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private String startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private String endDate;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    @NotNull
    @Column(name = "is_compleated", nullable = false)
    private Boolean isCompleated;

    @ManyToOne
    private Employee owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public VacationRequests startDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public VacationRequests endDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public VacationRequests duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean isIsApproved() {
        return isApproved;
    }

    public VacationRequests isApproved(Boolean isApproved) {
        this.isApproved = isApproved;
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean isIsCompleated() {
        return isCompleated;
    }

    public VacationRequests isCompleated(Boolean isCompleated) {
        this.isCompleated = isCompleated;
        return this;
    }

    public void setIsCompleated(Boolean isCompleated) {
        this.isCompleated = isCompleated;
    }

    public Employee getOwner() {
        return owner;
    }

    public VacationRequests owner(Employee employee) {
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
        VacationRequests vacationRequests = (VacationRequests) o;
        if (vacationRequests.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vacationRequests.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VacationRequests{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", duration=" + getDuration() +
            ", isApproved='" + isIsApproved() + "'" +
            ", isCompleated='" + isIsCompleated() + "'" +
            "}";
    }
}
