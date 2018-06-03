package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EmployeePossition.
 */
@Entity
@Table(name = "employee_possition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmployeePossition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "position_name", nullable = false)
    private String positionName;

    @NotNull
    @Column(name = "position_name_native", nullable = false)
    private String positionNameNative;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public EmployeePossition code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPositionName() {
        return positionName;
    }

    public EmployeePossition positionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionNameNative() {
        return positionNameNative;
    }

    public EmployeePossition positionNameNative(String positionNameNative) {
        this.positionNameNative = positionNameNative;
        return this;
    }

    public void setPositionNameNative(String positionNameNative) {
        this.positionNameNative = positionNameNative;
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
        EmployeePossition employeePossition = (EmployeePossition) o;
        if (employeePossition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeePossition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeePossition{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", positionName='" + getPositionName() + "'" +
            ", positionNameNative='" + getPositionNameNative() + "'" +
            "}";
    }
}
