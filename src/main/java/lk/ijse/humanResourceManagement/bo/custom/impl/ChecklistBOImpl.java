package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.ChecklistBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.ChecklistDAO;
import lk.ijse.humanResourceManagement.dto.ChecklistDto;
import lk.ijse.humanResourceManagement.entity.Checklist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChecklistBOImpl implements ChecklistBO {
    ChecklistDAO checklistDAO = (ChecklistDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.CHECKLIST);
    @Override
    public boolean saveChecklist(ChecklistDto dto) throws SQLException {
        return checklistDAO.save(new Checklist(dto.getChecklist_id(),dto.getOnboardingTask(),dto.getDueDate(),dto.getStatus()));
    }

    @Override
    public String generateNextChecklistId() throws SQLException {
        return checklistDAO.generateId();
    }


    @Override
    public List<ChecklistDto> loadAllTasks() throws SQLException {
        ArrayList<ChecklistDto> checklistDtos = new ArrayList<>();
        List<Checklist> checklists = checklistDAO.loadAll();
        for (Checklist checklist : checklists) {
            checklistDtos.add(new ChecklistDto(checklist.getChecklist_id(),checklist.getOnboardingTask(),checklist.getDueDate(),checklist.getStatus()));
        }
        return checklistDtos;
    }

    @Override
    public ChecklistDto searchTask(String id) throws SQLException {
        Checklist checklist = checklistDAO.search(id);
        ChecklistDto checklistDto = new ChecklistDto(checklist.getChecklist_id(),checklist.getOnboardingTask(),checklist.getDueDate(),checklist.getStatus());
        return checklistDto;
    }

    @Override
    public boolean deleteTask(String id) throws SQLException {
        return checklistDAO.delete(id);
    }

    @Override
    public boolean updateTask(String checklistId, String status) throws SQLException {
        return checklistDAO.update(new Checklist(checklistId,status));
    }
}
