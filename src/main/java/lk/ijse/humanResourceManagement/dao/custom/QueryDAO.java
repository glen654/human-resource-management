package lk.ijse.humanResourceManagement.dao.custom;

import lk.ijse.humanResourceManagement.dao.SuperDAO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
     EmployeeDto searchEmployeeBySalaryId(String id) throws SQLException;
}
