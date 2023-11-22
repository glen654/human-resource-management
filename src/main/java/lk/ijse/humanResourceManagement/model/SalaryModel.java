package lk.ijse.humanResourceManagement.model;

import com.mysql.cj.xdevapi.Collection;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public boolean saveSalary(SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO compensationAndBenefits VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCompensation_id());
        pstm.setString(2, dto.getEmp_id());
        pstm.setDouble(3,dto.getSalary());
        pstm.setString(4, dto.getStatus());
        pstm.setDouble(5,dto.getSalaryDeduction());
        pstm.setDouble(6,dto.getEpf());
        pstm.setDouble(7,dto.getOverTime());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public String generateNextSalaryId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT compensation_id FROM compensationAndBenefits ORDER BY compensation_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSalaryId(resultSet.getString(1));
        }
        return splitSalaryId(null);
    }

    private String splitSalaryId(String currentSalaryId) {
        if(currentSalaryId != null) {
            String[] split = currentSalaryId.split("S0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "S00" + id;
        } else {
            return "S001";
        }
    }

    public List<SalaryDto> loadAllSalary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM compensationAndBenefits";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SalaryDto> salaryList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            salaryList.add(new SalaryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7)
            ));
        }

        return salaryList;
    }

    public SalaryDto searchSalary(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM compensationAndBenefits WHERE compensation_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        SalaryDto dto = null;

        if(resultSet.next()) {
            String compensation_id = resultSet.getString(1);
            String emp_id = resultSet.getString(2);
            double salary = resultSet.getDouble(3);
            String status = resultSet.getString(4);
            double salaryDeduction = resultSet.getDouble(5);
            double epf = resultSet.getDouble(6);
            double overTime = resultSet.getDouble(7);

            dto = new SalaryDto(compensation_id,emp_id,salary,status,salaryDeduction,epf,overTime);
        }
        return dto;
    }

    public EmployeeDto searchEmployeeBySalaryId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT e.emp_id,e.firstName, e.lastName, e.emp_contact, e.email  FROM employee e" +
                " JOIN compensationAndBenefits c ON e.emp_id = c.emp_id WHERE c.compensation_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

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

    public boolean deleteSalary(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM compensationAndBenefits WHERE compensation_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

}
