package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.AttendanceDto;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceModel {

    public String generateAttendanceId(){
        long timestamp = System.currentTimeMillis();

        String prefix  = "A";
        String timestampString = String.valueOf(timestamp);
        int maxLength = 10;

        String truncatedId = prefix + timestampString.substring(0, Math.min(timestampString.length(), maxLength - prefix.length()));

        return truncatedId;
    }
    public boolean saveAttendnace(LocalDateTime scannedTime, String qrCodeData) throws SQLException {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String attendanceId = generateAttendanceId();

            LocalDate attendanceDate = scannedTime.toLocalDate();
            String formattedClockInTime = scannedTime.toLocalTime().toString();
            String formattedClockOutTime = null;

            String employeeId = extractEmployeeId(qrCodeData);

            if (attendanceExists(connection, employeeId, attendanceDate)) {
                updateAttendance(connection, employeeId, attendanceDate, formattedClockInTime);
            } else {
                insertAttendance(connection, attendanceId, attendanceDate, formattedClockInTime, formattedClockOutTime, employeeId);
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private String extractEmployeeId(String qrCodeData) {
        return qrCodeData.substring(qrCodeData.lastIndexOf(" ") + 1);
    }

    private boolean attendanceExists(Connection connection, String employeeId, LocalDate date) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM attendance WHERE emp_id = ? AND date = ?")) {
            preparedStatement.setString(1, employeeId);
            preparedStatement.setDate(2, Date.valueOf(date));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private void updateAttendance(Connection connection, String employeeId, LocalDate date, String formattedClockInTime) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE attendance SET clockOutTime = ? WHERE emp_id = ? AND date = ?")) {
            preparedStatement.setString(1, formattedClockInTime);
            preparedStatement.setString(2, employeeId);
            preparedStatement.setDate(3, Date.valueOf(date));
            preparedStatement.executeUpdate();
        }
    }

    private void insertAttendance(Connection connection, String attendanceId, LocalDate date, String formattedClockInTime, String formattedClockOutTime, String employeeId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO attendance VALUES(?,?,?,?,?)")) {
            preparedStatement.setString(1, attendanceId);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setString(3, formattedClockInTime);
            preparedStatement.setString(4, formattedClockOutTime);
            preparedStatement.setString(5, employeeId);
            preparedStatement.executeUpdate();
        }
    }
}
