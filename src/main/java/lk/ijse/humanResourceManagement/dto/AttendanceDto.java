package lk.ijse.humanResourceManagement.dto;

import java.time.LocalDate;


public class AttendanceDto {
    private String attendance_id;
    private LocalDate date;
    private String clockInTime;
    private String clockOutTime;
    private String emp_id;

    public AttendanceDto() {
    }

    public AttendanceDto(String attendance_id, LocalDate date, String clockInTime, String clockOutTime, String emp_id) {
        this.attendance_id = attendance_id;
        this.date = date;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.emp_id = emp_id;
    }

    public String getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(String attendance_id) {
        this.attendance_id = attendance_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }

    public String getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(String clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    @Override
    public String toString() {
        return "AttendanceDto{" +
                "attendance_id='" + attendance_id + '\'' +
                ", date=" + date +
                ", clockInTime='" + clockInTime + '\'' +
                ", clockOutTime='" + clockOutTime + '\'' +
                ", emp_id='" + emp_id + '\'' +
                '}';
    }
}
