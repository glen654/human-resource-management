package lk.ijse.humanResourceManagement.dao.custom;

import lk.ijse.humanResourceManagement.dao.CrudDAO;
import lk.ijse.humanResourceManagement.dto.TrainingEnrollmentDto;
import lk.ijse.humanResourceManagement.entity.TrainingEnrollment;

import java.sql.SQLException;
import java.util.List;

public interface TrainingEnrollmentDAO extends CrudDAO<TrainingEnrollment> {
    boolean updateEnrollment(String enrollmentId,String status) throws SQLException;

}
