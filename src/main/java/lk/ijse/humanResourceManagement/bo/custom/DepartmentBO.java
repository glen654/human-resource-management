package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentBO extends SuperBO {
    boolean saveDepartment(DepartmentDto dto) throws SQLException;

    List<DepartmentDto> loadAllDepartments() throws SQLException;

    DepartmentDto searchDepartment(String id) throws SQLException;

    boolean deleteDepartment(String id) throws SQLException;

    boolean updateDepartment(DepartmentDto dto) throws SQLException;

    int getDepartmentCount() throws SQLException;
}
