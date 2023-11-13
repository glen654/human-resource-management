package lk.ijse.humanResourceManagement.dto;


import java.sql.Date;
import java.time.LocalDate;

public class EmployeeDto {
    private String empId;
    private String firstName;
    private String lastName;
    private int empContact;
    private String empQualification;
    private String empHistory;
    private String departmentId;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private double salary;

    private String jobRole;

    public EmployeeDto(String id, String firstName, String lastName, int contact, String qualification, String history, String depId, LocalDate dob, String gender, String email, double salary, String role) {
    }

    public EmployeeDto(String empId, String firstName, String lastName, int empContact, String empQualification, String empHistory, String departmentId, Date dateOfBirth, String gender, String email, double salary, String jobRole) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.empContact = empContact;
        this.empQualification = empQualification;
        this.empHistory = empHistory;
        this.departmentId = departmentId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.jobRole = jobRole;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public int getEmpContact() {
        return empContact;
    }

    public void setEmpContact(int empContact) {
        this.empContact = empContact;
    }

    public String getEmpQualification() {
        return empQualification;
    }

    public void setEmpQualification(String empQualification) {
        this.empQualification = empQualification;
    }

    public String getEmpHistory() {
        return empHistory;
    }

    public void setEmpHistory(String empHistory) {
        this.empHistory = empHistory;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "empId='" + empId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", empContact=" + empContact +
                ", empQualification='" + empQualification + '\'' +
                ", empHistory='" + empHistory + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", jobRole='" + jobRole + '\'' +
                '}';
    }
}
