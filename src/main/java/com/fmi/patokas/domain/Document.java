package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_location")
    private String fileLocation;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "marked_for_delete")
    private Boolean markedForDelete;

    @ManyToOne
    private Employee owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public Document fileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Document createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean isMarkedForDelete() {
        return markedForDelete;
    }

    public Document markedForDelete(Boolean markedForDelete) {
        this.markedForDelete = markedForDelete;
        return this;
    }

    public void setMarkedForDelete(Boolean markedForDelete) {
        this.markedForDelete = markedForDelete;
    }

    public Employee getOwner() {
        return owner;
    }

    public Document owner(Employee employee) {
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
        Document document = (Document) o;
        if (document.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", fileLocation='" + getFileLocation() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", markedForDelete='" + isMarkedForDelete() + "'" +
            "}";
    }
}
