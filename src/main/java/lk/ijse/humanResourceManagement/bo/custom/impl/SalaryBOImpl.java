package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.SalaryBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.QueryDAO;
import lk.ijse.humanResourceManagement.dao.custom.SalaryDAO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.SalaryDto;
import lk.ijse.humanResourceManagement.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {
    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.SALARY);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.QUERY);
    @Override
    public boolean saveSalary(SalaryDto dto) throws SQLException {
        return salaryDAO.save(new Salary(dto.getCompensation_id(),dto.getEmp_id(),dto.getSalary(),dto.getStatus(),dto.getSalaryDeduction(),dto.getEpf(),dto.getOverTime()));
    }

    @Override
    public String generateNextSalaryId() throws SQLException {
        return salaryDAO.generateId();
    }

    @Override
    public List<SalaryDto> loadAllSalary() throws SQLException {
        ArrayList<SalaryDto> salaryDtos = new ArrayList<>();
        List<Salary> salaries = salaryDAO.loadAll();
        for (Salary salary : salaries) {
            salaryDtos.add(new SalaryDto(salary.getCompensation_id(),salary.getEmp_id(),salary.getSalary(),salary.getStatus(),salary.getSalaryDeduction(),salary.getEpf(),salary.getOverTime()));
        }
        return salaryDtos;
    }

    @Override
    public SalaryDto searchSalary(String id) throws SQLException {
        Salary salary = salaryDAO.search(id);
        SalaryDto salaryDto = new SalaryDto(salary.getCompensation_id(),salary.getEmp_id(),salary.getSalary(),salary.getStatus(),salary.getSalaryDeduction(),salary.getEpf(),salary.getOverTime());
        return salaryDto;
    }

    @Override
    public EmployeeDto searchEmployeeBySalaryId(String id) throws SQLException {
        return queryDAO.searchEmployeeBySalaryId(id);
    }

    @Override
    public boolean deleteSalary(String id) throws SQLException {
        return salaryDAO.delete(id);
    }
}
