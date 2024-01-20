package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.ChecklistDto;

import java.sql.SQLException;
import java.util.List;

public interface ChecklistBO extends SuperBO {
    boolean saveChecklist(ChecklistDto dto) throws SQLException;

    String generateNextChecklistId() throws SQLException;

    List<ChecklistDto> loadAllTasks() throws SQLException;

    ChecklistDto searchTask(String id) throws SQLException;

    boolean deleteTask(String id) throws SQLException;

    boolean updateTask(String checklistId,String status) throws SQLException;
}
