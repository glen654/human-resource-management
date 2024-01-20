package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.DepartmentBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.DepartmentDAO;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.entity.Department;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentBOImpl implements DepartmentBO {
    DepartmentDAO departmentDAO = (DepartmentDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.DEPARTMENT);
    @Override
    public boolean saveDepartment(DepartmentDto dto) throws SQLException {
        return departmentDAO.save(new Department(dto.getId(),dto.getName(),dto.getDesc()));
    }

    @Override
    public List<DepartmentDto> loadAllDepartments() throws SQLException {
        ArrayList<DepartmentDto> departmentDtos = new ArrayList<>();
        List<Department> departments = departmentDAO.loadAll();
        for (Department department : departments) {
            departmentDtos.add(new DepartmentDto(department.getId(),department.getName(),department.getDesc()));
        }
        return departmentDtos;
    }

    @Override
    public DepartmentDto searchDepartment(String id) throws SQLException {
        Department department = departmentDAO.search(id);
        DepartmentDto departmentDto = new DepartmentDto(department.getId(),department.getName(),department.getDesc());
        return departmentDto;
    }

    @Override
    public boolean deleteDepartment(String id) throws SQLException {
        return departmentDAO.delete(id);
    }

    @Override
    public boolean updateDepartment(DepartmentDto dto) throws SQLException {
        return departmentDAO.update(new Department(dto.getId(),dto.getName(),dto.getDesc()));
    }

    @Override
    public int getDepartmentCount() throws SQLException {
        return departmentDAO.getCount();
    }
}
