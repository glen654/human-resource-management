package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DepartmentModel {
    public boolean saveDepartment(DepartmentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO department VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getDesc());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<DepartmentDto> loadAllDepartments() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM department";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<DepartmentDto> depList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            depList.add(new DepartmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }

        return depList;
    }

    public DepartmentDto searchDepartment(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM department WHERE department_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        DepartmentDto dto = null;

        if(resultSet.next()) {
            String dep_id = resultSet.getString(1);
            String dep_name = resultSet.getString(2);
            String dep_desc = resultSet.getString(3);

            dto = new DepartmentDto(dep_id,dep_name,dep_desc);
        }
        return dto;
    }

    public boolean deleteDepartment(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM department WHERE department_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateDepartment(DepartmentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE department SET departmentName = ? , description = ? WHERE department_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getName());
        pstm.setString(2, dto.getDesc());
        pstm.setString(3, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public int getDepartmentCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM department";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
