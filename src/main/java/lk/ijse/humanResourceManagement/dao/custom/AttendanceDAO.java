package lk.ijse.humanResourceManagement.dao.custom;


import lk.ijse.humanResourceManagement.dao.CrudDAO;
import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.entity.Attendance;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AttendanceDAO extends CrudDAO<Attendance> {
     String generateAttendanceId();

     boolean saveAttendnace(LocalDateTime scannedTime, String qrCodeData) throws SQLException;

     String extractId(String qrCodeData);

     boolean insert(String attendanceId, LocalDate date, String formattedClockInTime, String formattedClockOutTime, String employeeId) throws SQLException;

     boolean update(String employeeId, LocalDate date, String formattedClockInTime) throws SQLException;

     boolean exists(String employeeId, LocalDate date) throws SQLException;
}
