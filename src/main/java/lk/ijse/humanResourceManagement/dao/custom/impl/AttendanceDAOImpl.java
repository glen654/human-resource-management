package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.AttendanceDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.entity.Attendance;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public String generateAttendanceId(){
        long timestamp = System.currentTimeMillis();

        String prefix  = "A";
        String timestampString = String.valueOf(timestamp);
        int maxLength = 10;

        String truncatedId = prefix + timestampString.substring(0, Math.min(timestampString.length(), maxLength - prefix.length()));

        return truncatedId;
    }
    @Override
    public boolean saveAttendnace(LocalDateTime scannedTime, String qrCodeData) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String attendanceId = generateId();

        LocalDate attendanceDate = scannedTime.toLocalDate();
        String formattedClockInTime = scannedTime.toLocalTime().toString();
        String formattedClockOutTime = null;

        String employeeId = extractId(qrCodeData);

        if (exists(employeeId, attendanceDate)) {
            update(employeeId, attendanceDate, formattedClockInTime);
        } else {
            insert(attendanceId, attendanceDate, formattedClockInTime, formattedClockOutTime, employeeId);
        }

        connection.commit();

        if (preparedStatement != null) preparedStatement.close();


        return true;
    }

    public String extractId(String qrCodeData) {
        return qrCodeData.substring(qrCodeData.lastIndexOf(" ") + 1);
    }
    public boolean insert(String attendanceId,LocalDate date,String formattedClockInTime,String formattedClockOutTime,String employeeId) throws SQLException {
        LocalDateTime dateTime = date.atStartOfDay();
        return SQLUtil.execute("INSERT INTO attendance VALUES(?,?,?,?,?)",attendanceId,dateTime,formattedClockInTime,formattedClockOutTime,employeeId);
    }

    public boolean update(String employeeId,LocalDate date,String formattedClockInTime) throws SQLException {
        LocalDateTime dateTime = date.atStartOfDay();
        return SQLUtil.execute("UPDATE attendance SET clockOutTime = ? WHERE emp_id = ? AND date = ?",formattedClockInTime,employeeId,dateTime);
    }

    public boolean exists(String employeeId,LocalDate date) throws SQLException {
        LocalDateTime dateTime = date.atStartOfDay();
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM attendance WHERE emp_id = ? AND date = ?",employeeId,dateTime);
        return resultSet.next();
    }
    @Override
    public boolean save(Attendance entity) throws SQLException {
        return false;
    }

    @Override
    public String generateId() throws SQLException {
        long timestamp = System.currentTimeMillis();

        String prefix  = "A";
        String timestampString = String.valueOf(timestamp);
        int maxLength = 10;

        String truncatedId = prefix + timestampString.substring(0, Math.min(timestampString.length(), maxLength - prefix.length()));

        return truncatedId;
    }

    @Override
    public String splitId(String currentId) {
        return null;
    }

    @Override
    public List<Attendance> loadAll() throws SQLException {
        return null;
    }

    @Override
    public Attendance search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Attendance entity) throws SQLException {
        return false;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }
}
