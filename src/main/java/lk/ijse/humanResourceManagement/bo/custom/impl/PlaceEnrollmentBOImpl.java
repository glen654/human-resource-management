package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.PlaceEnrollmentBO;
import lk.ijse.humanResourceManagement.controller.EnrollmentIdGenerator;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.ProgramDAO;
import lk.ijse.humanResourceManagement.dao.custom.TrainingEnrollmentDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.entity.Program;
import lk.ijse.humanResourceManagement.entity.TrainingEnrollment;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceEnrollmentBOImpl implements PlaceEnrollmentBO {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.PROGRAM);
    TrainingEnrollmentDAO enrollmentDAO = (TrainingEnrollmentDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.TRAINING_ENROLLMENT);

    @Override
    public boolean placeEnrollment(ProgramDto programDto) throws SQLException {
        String program_id = programDto.getProgram_id();
        String emp_id = programDto.getEmp_id();
        String name = programDto.getName();

        Connection connection = null;

        connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        boolean isProgramSaved = programDAO.save(new Program(programDto.getProgram_id(), programDto.getName(), programDto.getDescription(), programDto.getTrainers(), programDto.getDuration(), programDto.getEmp_id()));

        if (isProgramSaved) {
            String enrollmentId = EnrollmentIdGenerator.generateEnrollmentId();
            boolean isSaved = enrollmentDAO.save(new TrainingEnrollment(enrollmentId, emp_id, program_id, name, "Incomplete"));

            if (isSaved) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        }

        if (connection != null) {
            connection.rollback();
            connection.setAutoCommit(true);
        }

        return false;
    }
}







