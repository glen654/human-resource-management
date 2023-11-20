package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ChecklistDto;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;
import lk.ijse.humanResourceManagement.dto.ProgramDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChecklistModel {
    public boolean saveChecklist(ChecklistDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO onboardingChecklist VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getChecklist_id());
        pstm.setString(2, dto.getOnboardingTask());
        pstm.setString(3, String.valueOf(dto.getDueDate()));
        pstm.setString(4, dto.getStatus());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public String generateNextChecklistId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT checklist_id FROM onboardingChecklist ORDER BY checklist_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitChecklistId(resultSet.getString(1));
        }
        return splitChecklistId(null);
    }

    private String splitChecklistId(String currentChecklistId) {
        if(currentChecklistId != null) {
            String[] split = currentChecklistId.split("C0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "C00" + id;
        } else {
            return "C001";
        }
    }

    public List<ChecklistDto> loadAllTasks() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM onboardingChecklist";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ChecklistDto> taskList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            taskList.add(new ChecklistDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3).toLocalDate(),
                    resultSet.getString(4)
            ));
        }

        return taskList;
    }

    public ChecklistDto searchTask(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM onboardingChecklist WHERE checklist_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        ChecklistDto dto = null;

        if(resultSet.next()) {
            String checklist_id = resultSet.getString(1);
            String onboardingTasks = resultSet.getString(2);
            LocalDate dueDate = resultSet.getDate(3).toLocalDate();
            String status = resultSet.getString(4);

            dto = new ChecklistDto(checklist_id,onboardingTasks,dueDate,status);
        }
        return dto;
    }

    public boolean deleteTask(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM onboardingChecklist WHERE checklist_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateTask(String checklistId,String status) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE onboardingChecklist SET status = ? WHERE checklist_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,status);
        pstm.setString(2,checklistId);

        return pstm.executeUpdate() > 0;
    }
}
