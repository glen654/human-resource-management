package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.DepartmentDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.entity.Checklist;
import lk.ijse.humanResourceManagement.entity.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {
    @Override
    public boolean save(Department entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO department (department_id,departmentName,description) VALUES (?,?,?)",
                        entity.getId(),entity.getName(),entity.getDesc());
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
    public List<Department> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM department");
        ArrayList<Department> getAllDepartment = new ArrayList<>();
        while (rst.next()){
            Department department = new Department(rst.getString("department_id"), rst.getString("departmentName"), rst.getString("description"));
            getAllDepartment.add(department);
        }
        return getAllDepartment;
    }

    @Override
    public Department search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM department WHERE department_id=?",id);
        rst.next();
        return new Department(rst.getString("department_id"),rst.getString("departmentName"),rst.getString("description"));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM department WHERE department_id=?",id);
    }

    @Override
    public boolean update(Department entity) throws SQLException {
        return SQLUtil.execute("UPDATE department SET departmentName = ?, description = ? WHERE department_id=?",
                entity.getName(),entity.getDesc(),entity.getId());
    }

    @Override
    public int getCount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM department" );
        if(resultSet.next()){
            return resultSet.getInt(1);
        }

        return 0;
    }
}
