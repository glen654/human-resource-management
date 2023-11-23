package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String empId = dto.getId();
        if(empId != null) {
            String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, dto.getId());
            pstm.setString(2, dto.getFirstName());
            pstm.setString(3, dto.getLastName());
            pstm.setInt(4, dto.getContact());
            pstm.setString(5, dto.getQualification());
            pstm.setString(6, dto.getHistory());
            pstm.setString(7, dto.getDepId());
            pstm.setDate(8, Date.valueOf(dto.getDateOfBirth()));
            pstm.setString(9, dto.getGender());
            pstm.setString(10, dto.getEmail());
            pstm.setDouble(11, dto.getSalary());
            pstm.setString(12, dto.getJobRole());

            boolean isSaved = pstm.executeUpdate() > 0;

            return isSaved;
        }else{
            System.out.println("empId is null");
            return false;
        }
    }

    public String generateNextEmployeeId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    private String splitEmployeeId(String currentEmployeeId) {
        if(currentEmployeeId != null) {
            String[] split = currentEmployeeId.split("E0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "E00" + id;
        } else {
            return "E001";
        }
    }

    public List<EmployeeDto> loadAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT emp_id,firstName,job_role,department_id FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);


        List<EmployeeDto> empList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
               String empId = resultSet.getString(1);
               String first_name = resultSet.getString(2);
               String jobRole = resultSet.getString(3);
               String dep_id = resultSet.getString(4);

               EmployeeDto employeeDto = new EmployeeDto( empId,first_name,jobRole,dep_id);
               empList.add(employeeDto);
        }

        return empList;
    }

    public EmployeeDto searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String first_name = resultSet.getString(2);
            String last_name = resultSet.getString(3);
            int contact = resultSet.getInt(4);
            String qualification = resultSet.getString(5);
            String history = resultSet.getString(6);
            String dep_id = resultSet.getString(7);
            LocalDate dob = resultSet.getDate(8).toLocalDate();
            String gender = resultSet.getString(9);
            String email = resultSet.getString(10);
            double salary = resultSet.getDouble(11);
            String jobRole = resultSet.getString(12);

            dto = new EmployeeDto(emp_id,first_name,last_name,contact,qualification,history,dep_id,dob,gender,email,salary,jobRole);
        }
        return dto;
    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET emp_id = ?, firstName = ?, lastName = ?, emp_contact = ?, emp_qualification = ?, emp_history = ?, department_id = ?, date_of_birth = ?, gender = ?, email = ?, salary = ?, job_role = ?  WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getFirstName());
        pstm.setString(3,dto.getLastName());
        pstm.setInt(4,dto.getContact());
        pstm.setString(5,dto.getQualification());
        pstm.setString(6,dto.getHistory());
        pstm.setString(7,dto.getDepId());
        pstm.setDate(8, Date.valueOf(dto.getDateOfBirth()));
        pstm.setString(9,dto.getGender());
        pstm.setString(10,dto.getEmail());
        pstm.setString(11, String.valueOf(dto.getSalary()));
        pstm.setString(12,dto.getJobRole());

        return pstm.executeUpdate() > 0;
    }


    public int getEmployeeCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return resultSet.getInt(1);
        }

            return 0;
    }



}
