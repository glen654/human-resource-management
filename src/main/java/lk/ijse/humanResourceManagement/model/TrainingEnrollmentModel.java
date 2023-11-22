package lk.ijse.humanResourceManagement.model;


import lk.ijse.humanResourceManagement.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

;

public class TrainingEnrollmentModel {
    public boolean saveTrainingEnrollment(String enrollmentId,String empId, String programId, String name) throws SQLException, SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO trainingEnrollment VALUES (?,?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1,enrollmentId);
            pstm.setString(2, empId);
            pstm.setString(3, programId);
            pstm.setString(4, name);
            pstm.setString(5, "Incomplete");

            return pstm.executeUpdate() > 0;
        }
    }
}
