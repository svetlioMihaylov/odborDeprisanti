package com.fmi.patokas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.fmi.patokas.domain.enumeration.Position;

import com.fmi.patokas.domain.enumeration.Sex;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

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

    @NotNull
    @Column(name = "citizenship", nullable = false)
    private String citizenship;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "education", nullable = false)
    private String education;

    @NotNull
    @Column(name = "education_native", nullable = false)
    private String educationNative;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "t_shirt_siz")
    private String tShirtSiz;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactInformation contactInformation;

    @OneToOne
    @JoinColumn(unique = true)
    private EmployeePhoto employeePhoto;

    @OneToOne
    @JoinColumn(unique = true)
    private WorkDetails workDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private EmergancyContact emargencyContact;

    @OneToOne
    @JoinColumn(unique = true)
    private IDCard idcard;

    @OneToOne
    @JoinColumn(unique = true)
    private FinancialDetails financialDetails;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Benefit> benefits = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Document> documents = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExternalPerson> externalPeople = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmployeeNote> notes = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VacationRequests> vacationRequests = new HashSet<>();

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

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Employee middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNameNative() {
        return firstNameNative;
    }

    public Employee firstNameNative(String firstNameNative) {
        this.firstNameNative = firstNameNative;
        return this;
    }

    public void setFirstNameNative(String firstNameNative) {
        this.firstNameNative = firstNameNative;
    }

    public String getMiddleNameNative() {
        return middleNameNative;
    }

    public Employee middleNameNative(String middleNameNative) {
        this.middleNameNative = middleNameNative;
        return this;
    }

    public void setMiddleNameNative(String middleNameNative) {
        this.middleNameNative = middleNameNative;
    }

    public String getLastNameNative() {
        return lastNameNative;
    }

    public Employee lastNameNative(String lastNameNative) {
        this.lastNameNative = lastNameNative;
        return this;
    }

    public void setLastNameNative(String lastNameNative) {
        this.lastNameNative = lastNameNative;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public Employee citizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Employee dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEducation() {
        return education;
    }

    public Employee education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducationNative() {
        return educationNative;
    }

    public Employee educationNative(String educationNative) {
        this.educationNative = educationNative;
        return this;
    }

    public void setEducationNative(String educationNative) {
        this.educationNative = educationNative;
    }

    public Position getPosition() {
        return position;
    }

    public Employee position(Position position) {
        this.position = position;
        return this;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Sex getSex() {
        return sex;
    }

    public Employee sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String gettShirtSiz() {
        return tShirtSiz;
    }

    public Employee tShirtSiz(String tShirtSiz) {
        this.tShirtSiz = tShirtSiz;
        return this;
    }

    public void settShirtSiz(String tShirtSiz) {
        this.tShirtSiz = tShirtSiz;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public Employee contactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
        return this;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public EmployeePhoto getEmployeePhoto() {
        return employeePhoto;
    }

    public Employee employeePhoto(EmployeePhoto employeePhoto) {
        this.employeePhoto = employeePhoto;
        return this;
    }

    public void setEmployeePhoto(EmployeePhoto employeePhoto) {
        this.employeePhoto = employeePhoto;
    }

    public WorkDetails getWorkDetails() {
        return workDetails;
    }

    public Employee workDetails(WorkDetails workDetails) {
        this.workDetails = workDetails;
        return this;
    }

    public void setWorkDetails(WorkDetails workDetails) {
        this.workDetails = workDetails;
    }

    public EmergancyContact getEmargencyContact() {
        return emargencyContact;
    }

    public Employee emargencyContact(EmergancyContact emergancyContact) {
        this.emargencyContact = emergancyContact;
        return this;
    }

    public void setEmargencyContact(EmergancyContact emergancyContact) {
        this.emargencyContact = emergancyContact;
    }

    public IDCard getIdcard() {
        return idcard;
    }

    public Employee idcard(IDCard iDCard) {
        this.idcard = iDCard;
        return this;
    }

    public void setIdcard(IDCard iDCard) {
        this.idcard = iDCard;
    }

    public FinancialDetails getFinancialDetails() {
        return financialDetails;
    }

    public Employee financialDetails(FinancialDetails financialDetails) {
        this.financialDetails = financialDetails;
        return this;
    }

    public void setFinancialDetails(FinancialDetails financialDetails) {
        this.financialDetails = financialDetails;
    }

    public Set<Benefit> getBenefits() {
        return benefits;
    }

    public Employee benefits(Set<Benefit> benefits) {
        this.benefits = benefits;
        return this;
    }

    public Employee addBenefits(Benefit benefit) {
        this.benefits.add(benefit);
        benefit.setEmployee(this);
        return this;
    }

    public Employee removeBenefits(Benefit benefit) {
        this.benefits.remove(benefit);
        benefit.setEmployee(null);
        return this;
    }

    public void setBenefits(Set<Benefit> benefits) {
        this.benefits = benefits;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Employee documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Employee addDocuments(Document document) {
        this.documents.add(document);
        document.setOwner(this);
        return this;
    }

    public Employee removeDocuments(Document document) {
        this.documents.remove(document);
        document.setOwner(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<ExternalPerson> getExternalPeople() {
        return externalPeople;
    }

    public Employee externalPeople(Set<ExternalPerson> externalPeople) {
        this.externalPeople = externalPeople;
        return this;
    }

    public Employee addExternalPeople(ExternalPerson externalPerson) {
        this.externalPeople.add(externalPerson);
        externalPerson.setOwner(this);
        return this;
    }

    public Employee removeExternalPeople(ExternalPerson externalPerson) {
        this.externalPeople.remove(externalPerson);
        externalPerson.setOwner(null);
        return this;
    }

    public void setExternalPeople(Set<ExternalPerson> externalPeople) {
        this.externalPeople = externalPeople;
    }

    public Set<EmployeeNote> getNotes() {
        return notes;
    }

    public Employee notes(Set<EmployeeNote> employeeNotes) {
        this.notes = employeeNotes;
        return this;
    }

    public Employee addNotes(EmployeeNote employeeNote) {
        this.notes.add(employeeNote);
        employeeNote.setOwner(this);
        return this;
    }

    public Employee removeNotes(EmployeeNote employeeNote) {
        this.notes.remove(employeeNote);
        employeeNote.setOwner(null);
        return this;
    }

    public void setNotes(Set<EmployeeNote> employeeNotes) {
        this.notes = employeeNotes;
    }

    public Set<VacationRequests> getVacationRequests() {
        return vacationRequests;
    }

    public Employee vacationRequests(Set<VacationRequests> vacationRequests) {
        this.vacationRequests = vacationRequests;
        return this;
    }

    public Employee addVacationRequests(VacationRequests vacationRequests) {
        this.vacationRequests.add(vacationRequests);
        vacationRequests.setOwner(this);
        return this;
    }

    public Employee removeVacationRequests(VacationRequests vacationRequests) {
        this.vacationRequests.remove(vacationRequests);
        vacationRequests.setOwner(null);
        return this;
    }

    public void setVacationRequests(Set<VacationRequests> vacationRequests) {
        this.vacationRequests = vacationRequests;
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
        Employee employee = (Employee) o;
        if (employee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstNameNative='" + getFirstNameNative() + "'" +
            ", middleNameNative='" + getMiddleNameNative() + "'" +
            ", lastNameNative='" + getLastNameNative() + "'" +
            ", citizenship='" + getCitizenship() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", education='" + getEducation() + "'" +
            ", educationNative='" + getEducationNative() + "'" +
            ", position='" + getPosition() + "'" +
            ", sex='" + getSex() + "'" +
            ", tShirtSiz='" + gettShirtSiz() + "'" +
            "}";
    }
}
