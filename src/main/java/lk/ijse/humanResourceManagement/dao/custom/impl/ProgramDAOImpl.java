package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.ProgramDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.entity.Checklist;
import lk.ijse.humanResourceManagement.entity.Program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramDAOImpl implements ProgramDAO {
    @Override
    public boolean save(Program entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO trainingProgram (program_id,name,description,trainers,duration,emp_id) VALUES (?,?,?,?,?,?)",
                        entity.getProgram_id(),entity.getName(),entity.getDescription(),entity.getTrainers(),entity.getDuration(),entity.getEmp_id());
    }

    @Override
    public String generateId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT program_id FROM trainingProgram ORDER BY program_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("P0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "P00" + id;
        } else {
            return "P001";
        }
    }

    @Override
    public List<Program> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM trainingProgram");
        ArrayList<Program> getAllProgram = new ArrayList<>();
        while (rst.next()){
            Program program = new Program(rst.getString("program_id"), rst.getString("name"), rst.getString("description"),rst.getString("trainers"),rst.getString("duration"),rst.getString("emp_id"));
            getAllProgram.add(program);
        }
        return getAllProgram;
    }

    @Override
    public Program search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM trainingProgram WHERE program_id=?",id);
        rst.next();
        return new Program(rst.getString("program_id"),rst.getString("name"), rst.getString("description"),rst.getString("trainers"),rst.getString("duration"),rst.getString("emp_id"));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM trainingProgram WHERE program_id=?",id);
    }

    @Override
    public boolean update(Program entity) throws SQLException {
        return SQLUtil.execute("UPDATE trainingProgram SET name = ?,description = ?, trainers = ?, duration = ?, emp_id = ? WHERE program_id=?",
                entity.getName(),entity.getDescription(),entity.getTrainers(),entity.getDuration(),entity.getEmp_id(),entity.getProgram_id());
    }

    @Override
    public int getCount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM trainingProgram" );
        if(resultSet.next()){
            return resultSet.getInt(1);
        }

        return 0;

    }
}
