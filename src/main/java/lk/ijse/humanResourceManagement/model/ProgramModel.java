package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ProgramDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramModel {
    public boolean saveProgram(ProgramDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO trainingProgram VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getProgram_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getDescription());
        pstm.setString(4, dto.getTrainers());
        pstm.setString(5, dto.getDuration());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public String generateNextProgramId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT program_id FROM trainingProgram ORDER BY program_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitProgramId(resultSet.getString(1));
        }
        return splitProgramId(null);
    }

    private String splitProgramId(String currentProgramId) {
        if(currentProgramId != null) {
            String[] split = currentProgramId.split("P0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "P00" + id;
        } else {
            return "P001";
        }
    }
}
