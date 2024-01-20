package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.AttendacenBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.AttendanceDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceBOImpl implements AttendacenBO {
    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.ATTENDANCE);
    @Override
    public boolean saveAttendnace(LocalDateTime scannedTime, String qrCodeData) throws SQLException {
        return attendanceDAO.saveAttendnace(scannedTime,qrCodeData);
    }

}
