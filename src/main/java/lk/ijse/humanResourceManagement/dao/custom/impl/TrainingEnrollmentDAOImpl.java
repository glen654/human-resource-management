package lk.ijse.humanResourceManagement.dao.custom.impl;


import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.TrainingEnrollmentDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.TrainingEnrollmentDto;
import lk.ijse.humanResourceManagement.entity.Program;
import lk.ijse.humanResourceManagement.entity.TrainingEnrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class TrainingEnrollmentDAOImpl implements TrainingEnrollmentDAO {
    @Override
    public boolean updateEnrollment(String enrollmentId,String status) throws SQLException {
        return SQLUtil.execute("UPDATE trainingEnrollment SET status = ? WHERE enrollment_id = ?",
                status,enrollmentId);

    }
    @Override
    public boolean save(TrainingEnrollment entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO trainingEnrollment (enrollment_id,emp_id,program_id,name,status) VALUES (?,?,?,?,?)",
                        entity.getEnrollment_id(),entity.getEmp_id(),entity.getProgram_id(),entity.getName(),entity.getStatus("Incomplete"));
    }

    @Override
    public String generateId() throws SQLException {
        return null;
    }

    @Override
    public String splitId(String currentId) {
        return null;
    }

    @Override
    public List<TrainingEnrollment> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM trainingEnrollment");
        ArrayList<TrainingEnrollment> getAllEnrollment = new ArrayList<>();
        while (rst.next()){
            TrainingEnrollment enrollment = new TrainingEnrollment(rst.getString("enrollment_id"), rst.getString("emp_id"), rst.getString("program_id"),rst.getString("name"),rst.getString("status"));
            getAllEnrollment.add(enrollment);
        }
        return getAllEnrollment;
    }

    @Override
    public TrainingEnrollment search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM trainingEnrollment WHERE enrollment_id=?",id);
    }

    @Override
    public boolean update(TrainingEnrollment entity) throws SQLException {
        return false;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }
}
