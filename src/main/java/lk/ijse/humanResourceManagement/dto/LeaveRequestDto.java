package lk.ijse.humanResourceManagement.dto;

import java.time.LocalDate;

public class LeaveRequestDto {
    private String request_id;
    private String emp_id;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDate requestDate;

    public LeaveRequestDto() {
    }

    public LeaveRequestDto(String request_id, String emp_id, String leaveType, LocalDate startDate, LocalDate endDate, String status, LocalDate requestDate) {
        this.request_id = request_id;
        this.emp_id = emp_id;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.requestDate = requestDate;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "LeaveRequestDto{" +
                "request_id='" + request_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                '}';
    }
}
