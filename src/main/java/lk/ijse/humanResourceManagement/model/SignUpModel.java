package lk.ijse.humanResourceManagement.model;

import javafx.scene.control.TextField;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.SignUpDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpModel {
    public boolean saveUser(SignUpDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getFirstName());
        pstm.setString(2, dto.getLastName());
        pstm.setString(3, dto.getPosition());
        pstm.setString(4, dto.getUserName());
        pstm.setString(5, dto.getConfirmPassword());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

}
