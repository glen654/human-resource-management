package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.SalaryDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.SalaryDto;
import lk.ijse.humanResourceManagement.entity.Program;
import lk.ijse.humanResourceManagement.entity.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public boolean save(Salary entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO compensationAndBenefits (compensation_id,emp_id,salary,status,salaryDeduction,epf,overTime) VALUES (?,?,?,?,?,?,?)",
                        entity.getCompensation_id(),entity.getEmp_id(),entity.getSalary(),entity.getStatus(),entity.getSalaryDeduction(),entity.getEpf(),entity.getOverTime());
    }

    @Override
    public String generateId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT compensation_id FROM compensationAndBenefits ORDER BY compensation_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("S0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "S00" + id;
        } else {
            return "S001";
        }
    }

    @Override
    public List<Salary> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM compensationAndBenefits");
        ArrayList<Salary> getAllSalary = new ArrayList<>();
        while (rst.next()){
            Salary salary = new Salary(rst.getString("compensation_id"), rst.getString("emp_id"), rst.getDouble("salary"),rst.getString("status"),rst.getDouble("salaryDeduction"),rst.getDouble("epf"),rst.getDouble("overTime"));
            getAllSalary.add(salary);
        }
        return getAllSalary;
    }

    @Override
    public Salary search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM compensationAndBenefits WHERE compensation_id=?",id);
        rst.next();
        return new Salary(rst.getString("compensation_id"), rst.getString("emp_id"), rst.getDouble("salary"),rst.getString("status"),rst.getDouble("salaryDeduction"),rst.getDouble("epf"),rst.getDouble("overTime"));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM compensationAndBenefits WHERE compensation_id=?",id);
    }

    @Override
    public boolean update(Salary entity) throws SQLException {
        return false;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }
}
