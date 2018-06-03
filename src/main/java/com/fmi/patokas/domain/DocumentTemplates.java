package com.fmi.patokas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DocumentTemplates.
 */
@Entity
@Table(name = "document_templates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocumentTemplates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_type")
    private String templateType;

    @Column(name = "file_location")
    private String fileLocation;

    @Lob
    @Column(name = "content")
    private String content;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateType() {
        return templateType;
    }

    public DocumentTemplates templateType(String templateType) {
        this.templateType = templateType;
        return this;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public DocumentTemplates fileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getContent() {
        return content;
    }

    public DocumentTemplates content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
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
        DocumentTemplates documentTemplates = (DocumentTemplates) o;
        if (documentTemplates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentTemplates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentTemplates{" +
            "id=" + getId() +
            ", templateType='" + getTemplateType() + "'" +
            ", fileLocation='" + getFileLocation() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
