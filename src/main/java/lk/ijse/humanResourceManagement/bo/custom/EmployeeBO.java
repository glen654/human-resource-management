package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDto dto) throws SQLException;

    String generateNextEmployeeId() throws SQLException;

    List<EmployeeDto> loadAllEmployee() throws SQLException;

    EmployeeDto searchEmployee(String id) throws SQLException;

    boolean deleteEmployee(String id) throws SQLException;

    boolean updateEmployee(EmployeeDto dto) throws SQLException;

    int getEmployeeCount() throws SQLException;
}
