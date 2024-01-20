package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.QueryDAO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public EmployeeDto searchEmployeeBySalaryId(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT e.emp_id,e.firstName, e.lastName, e.emp_contact, e.email  FROM employee e JOIN compensationAndBenefits c ON e.emp_id = c.emp_id WHERE c.compensation_id = ?",id);
        if(resultSet.next()){
            return new EmployeeDto(
                    resultSet.getString("emp_id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getInt("emp_contact"),
                    resultSet.getString("email")

            );
        }else{
            return  null;
        }
    }
}
