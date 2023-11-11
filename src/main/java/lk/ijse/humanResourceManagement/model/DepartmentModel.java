package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
