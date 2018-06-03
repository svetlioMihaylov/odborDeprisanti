package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fmi.patokas.domain.enumeration.EmployeeNoteType;

/**
 * A EmployeeNote.
 */
@Entity
@Table(name = "employee_note")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmployeeNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_note_type")
    private EmployeeNoteType employeeNoteType;

    @ManyToOne
    private Employee owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public EmployeeNote title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public EmployeeNote text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public EmployeeNote date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EmployeeNoteType getEmployeeNoteType() {
        return employeeNoteType;
    }

    public EmployeeNote employeeNoteType(EmployeeNoteType employeeNoteType) {
        this.employeeNoteType = employeeNoteType;
        return this;
    }

    public void setEmployeeNoteType(EmployeeNoteType employeeNoteType) {
        this.employeeNoteType = employeeNoteType;
    }

    public Employee getOwner() {
        return owner;
    }

    public EmployeeNote owner(Employee employee) {
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
        EmployeeNote employeeNote = (EmployeeNote) o;
        if (employeeNote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeNote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeNote{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", text='" + getText() + "'" +
            ", date='" + getDate() + "'" +
            ", employeeNoteType='" + getEmployeeNoteType() + "'" +
            "}";
    }
}
