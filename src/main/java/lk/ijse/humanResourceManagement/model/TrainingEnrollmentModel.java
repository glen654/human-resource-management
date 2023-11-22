package lk.ijse.humanResourceManagement.model;


import lk.ijse.humanResourceManagement.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

;

public class TrainingEnrollmentModel {
    public boolean saveTrainingEnrollment(String empId, String programId, String name) throws SQLException, SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO trainingEnrollment VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, empId);
            pstm.setString(2, programId);
            pstm.setString(3, name);
            pstm.setString(4, "Incomplete"); // Set default status as 'Incompleted'

            return pstm.executeUpdate() > 0;
        }
    }
}
