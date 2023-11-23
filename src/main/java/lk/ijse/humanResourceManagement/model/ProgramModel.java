package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramModel {
    public boolean saveProgram(ProgramDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO trainingProgram VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getProgram_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getDescription());
        pstm.setString(4, dto.getTrainers());
        pstm.setString(5, dto.getDuration());
        pstm.setString(6,dto.getEmp_id());
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

    public List<ProgramDto> loadAllPrograms() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM trainingProgram";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ProgramDto> programList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            programList.add(new ProgramDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }

        return programList;
    }

    public ProgramDto searchProgram(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM trainingProgram WHERE program_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        ProgramDto dto = null;

        if(resultSet.next()) {
            String program_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            String trainers = resultSet.getString(4);
            String duration = resultSet.getString(5);
            String emp_id = resultSet.getString(6);

            dto = new ProgramDto(program_id,name,description,trainers,duration,emp_id);
        }
        return dto;
    }

    public boolean updateProgram(ProgramDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE trainingProgram SET  name = ?, description = ?, trainers = ?, duration = ?, emp_id = ? WHERE program_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2,dto.getDescription());
        pstm.setString(3,dto.getTrainers());
        pstm.setString(4,dto.getDuration());
        pstm.setString(5,dto.getEmp_id());
        pstm.setString(6,dto.getProgram_id());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteProgram(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM trainingProgram WHERE program_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

    public int getProgramCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM trainingProgram";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return resultSet.getInt(1);
        }

        return 0;
    }
}
