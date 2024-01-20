package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AttendacenBO extends SuperBO {
    boolean saveAttendnace(LocalDateTime scannedTime, String qrCodeData) throws SQLException;


}
