package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.SalaryDto;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
    boolean saveSalary(SalaryDto dto) throws SQLException;

    String generateNextSalaryId() throws SQLException;

    List<SalaryDto> loadAllSalary() throws SQLException;

    SalaryDto searchSalary(String id) throws SQLException;

    EmployeeDto searchEmployeeBySalaryId(String id) throws SQLException;

    boolean deleteSalary(String id) throws SQLException;
}
