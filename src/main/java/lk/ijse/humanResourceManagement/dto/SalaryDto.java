package lk.ijse.humanResourceManagement.dto;

public class SalaryDto {
    private String compensation_id;
    private String emp_id;
    private double salary;
    private String status;
    private double salaryDeduction;
    private double epf;
    private double overTime;


    public SalaryDto() {
    }

    public SalaryDto(String compensation_id, String emp_id, double salary, String status, double salaryDeduction, double epf, double overTime) {
        this.compensation_id = compensation_id;
        this.emp_id = emp_id;
        this.salary = salary;
        this.status = status;
        this.salaryDeduction = salaryDeduction;
        this.epf = epf;
        this.overTime = overTime;
    }

    public String getCompensation_id() {
        return compensation_id;
    }

    public void setCompensation_id(String compensation_id) {
        this.compensation_id = compensation_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSalaryDeduction() {
        return salaryDeduction;
    }

    public void setSalaryDeduction(double salaryDeduction) {
        this.salaryDeduction = salaryDeduction;
    }

    public double getEpf() {
        return epf;
    }

    public void setEpf(double epf) {
        this.epf = epf;
    }

    public double getOverTime() {
        return overTime;
    }

    public void setOverTime(double overTime) {
        this.overTime = overTime;
    }

    @Override
    public String toString() {
        return "SalaryDto{" +
                "compensation_id='" + compensation_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", salary=" + salary +
                ", status='" + status + '\'' +
                ", salaryDeduction=" + salaryDeduction +
                ", epf=" + epf +
                ", overTime=" + overTime +
                '}';
    }
}