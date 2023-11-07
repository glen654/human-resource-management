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

        String sql = "INSERT INTO user VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getTxtId());
        pstm.setString(2, dto.getTxtUsername());
        pstm.setString(3, dto.getTxtPassword());
        pstm.setString(4, dto.getPosition());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

}
