package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.TrainingEnrollmentDto;

import java.sql.SQLException;
import java.util.List;

public interface TrainingEnrollmentBO extends SuperBO {
    boolean saveTrainingEnrollment(String enrollmentId,String empId, String programId, String name) throws SQLException, SQLException;

    List<TrainingEnrollmentDto> loadAllEnrollment() throws SQLException;

    boolean updateEnrollment(String enrollmentId,String status) throws SQLException;

    boolean deleteEnrollment(String id) throws SQLException;
}
