package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.controller.EnrollmentIdGenerator;
import lk.ijse.humanResourceManagement.controller.TrainingProgramAddController;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.dto.TrainingEnrollmentDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceTrainingEnrollmentModel {
    private static ProgramModel programModel = new ProgramModel();
    private static TrainingEnrollmentModel enrollmentModel = new TrainingEnrollmentModel();


    public static boolean placeEnrollment(ProgramDto programDto) {
        String program_id = programDto.getProgram_id();
        String emp_id = programDto.getEmp_id();
        String name = programDto.getName();

        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Save the program details
            boolean isProgramSaved = programModel.saveProgram(programDto);

            if (isProgramSaved) {
                // Save the training enrollment details
                String enrollmentId = EnrollmentIdGenerator.generateEnrollmentId();
                boolean isSaved = enrollmentModel.saveTrainingEnrollment(enrollmentId,emp_id, program_id, name);

                if (isSaved) {
                    // Commit the transaction if both program and enrollment are saved successfully
                    connection.commit();
                    return true;
                } else {
                    // Rollback the transaction if enrollment saving fails
                    connection.rollback();
                    return false;
                }
            } else {
                // Rollback the transaction if program saving fails
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            // Handle the exception appropriately
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
