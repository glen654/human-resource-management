package lk.ijse.humanResourceManagement.entity;

import java.sql.Date;
import java.time.LocalDate;

public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private int contact;
    private String qualification;
    private String history;
    private String depId;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private double salary;
    private String jobRole;

    public Employee() {
    }

    public Employee(String id, String firstName, String lastName, int contact, String qualification, String history, String depId, LocalDate dateOfBirth, String gender, String email, double salary, String jobRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.qualification = qualification;
        this.history = history;
        this.depId = depId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.jobRole = jobRole;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }
}
