package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.EmployeeDAO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        return employeeDAO.save(new Employee(dto.getId(),dto.getFirstName(),dto.getLastName(),dto.getContact(),dto.getQualification(),dto.getHistory(),dto.getDepId(),dto.getDateOfBirth(),dto.getGender(),dto.getEmail(),dto.getSalary(),dto.getJobRole()));
    }

    @Override
    public String generateNextEmployeeId() throws SQLException {
        return employeeDAO.generateId();
    }

    @Override
    public List<EmployeeDto> loadAllEmployee() throws SQLException {
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> employees = employeeDAO.loadAll();
        for (Employee employee : employees) {
            employeeDtos.add(new EmployeeDto(employee.getId(),employee.getFirstName(),employee.getJobRole(),employee.getDepId()));
        }
        return employeeDtos;
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException {
        Employee employee = employeeDAO.search(id);
        EmployeeDto employeeDto = new EmployeeDto(employee.getId(),employee.getFirstName(),employee.getLastName(),employee.getContact(),employee.getQualification(),employee.getHistory(),employee.getDepId(),employee.getDateOfBirth(),employee.getGender(),employee.getEmail(),employee.getSalary(),employee.getJobRole());
        return employeeDto;
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
       return employeeDAO.update(new Employee(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getContact(), dto.getQualification(), dto.getHistory(), dto.getDepId(), dto.getDateOfBirth(), dto.getGender(), dto.getEmail(), dto.getSalary(),dto.getJobRole()));

    }

    @Override
    public int getEmployeeCount() throws SQLException {
        return employeeDAO.getCount();
    }
}
