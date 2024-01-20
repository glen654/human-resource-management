package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.TrainingEnrollmentBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.TrainingEnrollmentDAO;
import lk.ijse.humanResourceManagement.dto.TrainingEnrollmentDto;
import lk.ijse.humanResourceManagement.entity.TrainingEnrollment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainingEnrollmentBOImpl implements TrainingEnrollmentBO {
    TrainingEnrollmentDAO trainingEnrollmentDAO = (TrainingEnrollmentDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.TRAINING_ENROLLMENT);
    @Override
    public boolean saveTrainingEnrollment(String enrollmentId, String empId, String programId, String name) throws SQLException, SQLException {
        return trainingEnrollmentDAO.save(new TrainingEnrollment(enrollmentId,empId,programId,name,"Incomplete"));
    }

    @Override
    public List<TrainingEnrollmentDto> loadAllEnrollment() throws SQLException {
        ArrayList<TrainingEnrollmentDto> enrollmentDtos = new ArrayList<>();
        List<TrainingEnrollment> enrollments = trainingEnrollmentDAO.loadAll();
        for (TrainingEnrollment enrollment : enrollments) {
            enrollmentDtos.add(new TrainingEnrollmentDto(enrollment.getEnrollment_id(),enrollment.getEmp_id(),enrollment.getProgram_id(),enrollment.getName(),enrollment.getStatus("Incomplete")));
        }
        return enrollmentDtos;
    }

    @Override
    public boolean updateEnrollment(String enrollmentId,String status) throws SQLException {
        return trainingEnrollmentDAO.updateEnrollment(enrollmentId, status);
    }

    @Override
    public boolean deleteEnrollment(String id) throws SQLException {
        return trainingEnrollmentDAO.delete(id);
    }
}
