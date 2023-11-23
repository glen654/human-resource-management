package lk.ijse.humanResourceManagement.model;


import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.dto.TrainingEnrollmentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<TrainingEnrollmentDto> loadAllEnrollment() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM trainingEnrollment";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<TrainingEnrollmentDto> enrollmentList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            enrollmentList.add(new TrainingEnrollmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }

        return enrollmentList;
    }

    public boolean updateEnrollment(String enrollmentId,String status) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE trainingEnrollment SET status = ? WHERE enrollment_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,status);
        pstm.setString(2,enrollmentId);

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteEnrollment(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM trainingEnrollment WHERE enrollment_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }
}
