package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.ChecklistDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ChecklistDto;
import lk.ijse.humanResourceManagement.entity.Checklist;
import lk.ijse.humanResourceManagement.entity.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChecklistDAOImpl implements ChecklistDAO {
    @Override
    public boolean save(Checklist entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO onboardingChecklist (checklist_id,onboardingTasks,dueDate,status) VALUES (?,?,?,?)",
                        entity.getChecklist_id(),entity.getOnboardingTask(),entity.getDueDate(),entity.getStatus());
    }

    @Override
    public String generateId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT checklist_id FROM onboardingChecklist ORDER BY checklist_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("C0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "C00" + id;
        } else {
            return "C001";
        }
    }

    @Override
    public List<Checklist> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM onboardingChecklist");
        ArrayList<Checklist> getAllChecklist = new ArrayList<>();
        while (rst.next()){
            Checklist checklist = new Checklist(rst.getString("checklist_id"), rst.getString("onboardingTasks"), rst.getDate("dueDate").toLocalDate(),rst.getString("status"));
            getAllChecklist.add(checklist);
        }
        return getAllChecklist;
    }

    @Override
    public Checklist search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM onboardingChecklist WHERE checklist_id=?",id);
        rst.next();
        return new Checklist(rst.getString("checklist_id"),rst.getString("onboardingTasks"), rst.getDate("dueDate").toLocalDate(),rst.getString("status"));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM onboardingChecklist WHERE checklist_id=?",id);
    }

    @Override
    public boolean update(Checklist entity) throws SQLException {
        return SQLUtil.execute("UPDATE onboardingChecklist SET status = ? WHERE checklist_id=?",
                entity.getStatus(),entity.getChecklist_id());
    }

    @Override
    public int getCount() throws SQLException {
       return 0;
    }

}
