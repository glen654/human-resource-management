package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.EmployeeDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.entity.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO employee (emp_id,firstName,lastName,emp_contact,emp_qualification,emp_history,department_id,date_of_birth,gender,email,salary,job_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                        entity.getId(),entity.getFirstName(),entity.getLastName(),entity.getContact(),entity.getQualification(),entity.getHistory(),entity.getDepId(),entity.getDateOfBirth(),entity.getGender(),entity.getEmail(),entity.getSalary(),entity.getJobRole());
    }

    @Override
    public String generateId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("E0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "E00" + id;
        } else {
            return "E001";
        }
    }

    @Override
    public List<Employee> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        ArrayList<Employee> getAllEmployee = new ArrayList<>();
        while (rst.next()){
            Employee employee = new Employee(rst.getString("emp_id"), rst.getString("firstName"), rst.getString("lastName"),rst.getInt("emp_contact"),rst.getString("emp_qualification"),rst.getString("emp_history"),rst.getString("department_id"), rst.getDate("date_of_birth").toLocalDate(),rst.getString("gender"),rst.getString("email"),rst.getDouble("salary"),rst.getString("job_role"));
            getAllEmployee.add(employee);
        }
        return getAllEmployee;
    }

    @Override
    public Employee search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE emp_id=?",id);
        rst.next();
        return new Employee(rst.getString("emp_id"), rst.getString("firstName"), rst.getString("lastName"),rst.getInt("emp_contact"),rst.getString("emp_qualification"),rst.getString("emp_history"),rst.getString("department_id"), rst.getDate("date_of_birth").toLocalDate(),rst.getString("gender"),rst.getString("email"),rst.getDouble("salary"),rst.getString("job_role"));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM employee WHERE emp_id=?",id);
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute("UPDATE employee SET emp_id = ?, firstName = ?, lastName = ?,emp_contact = ?,emp_qualification = ?, emp_history = ?, department_id = ?, date_of_birth = ?, gender = ?, email = ?, salary = ?, job_role = ?  WHERE emp_id=?",
                entity.getId(),entity.getFirstName(),entity.getLastName(),entity.getContact(),entity.getQualification(),entity.getHistory(),entity.getDepId(),entity.getDateOfBirth(),entity.getGender(),entity.getEmail(),entity.getSalary(),entity.getJobRole());
    }

    @Override
    public int getCount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM employee" );
        if(resultSet.next()){
            return resultSet.getInt(1);
        }

        return 0;
    }
}
