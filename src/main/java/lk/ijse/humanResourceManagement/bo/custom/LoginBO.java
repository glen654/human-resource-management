package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.LoginDto;

import java.sql.SQLException;

public interface LoginBO  extends SuperBO {
    LoginDto searchUser(String userName, String password) throws SQLException;
}
