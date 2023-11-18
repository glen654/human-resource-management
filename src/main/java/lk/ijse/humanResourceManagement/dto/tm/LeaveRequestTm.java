package lk.ijse.humanResourceManagement.dto.tm;

import com.jfoenix.controls.JFXButton;

import java.time.LocalDate;

public class LeaveRequestTm {
    private String request_id;
    private String emp_id;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDate requestDate;

    private JFXButton btnUpdate;
    private JFXButton btnDelete;

    public LeaveRequestTm() {
    }

    public LeaveRequestTm(String request_id, String emp_id, String leaveType, LocalDate startDate, LocalDate endDate, String status, LocalDate requestDate, JFXButton btnUpdate, JFXButton btnDelete) {
        this.request_id = request_id;
        this.emp_id = emp_id;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.requestDate = requestDate;
        this.btnUpdate = btnUpdate;
        this.btnDelete = btnDelete;
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

    public JFXButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JFXButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JFXButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JFXButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "LeaveRequestTm{" +
                "request_id='" + request_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", requestDate=" + requestDate +
                ", btnUpdate=" + btnUpdate +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
