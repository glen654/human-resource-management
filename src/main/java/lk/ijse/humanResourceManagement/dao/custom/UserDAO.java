package lk.ijse.humanResourceManagement.dao.custom;

import lk.ijse.humanResourceManagement.dao.CrudDAO;
import lk.ijse.humanResourceManagement.dto.LoginDto;
import lk.ijse.humanResourceManagement.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    LoginDto searchUser(String userName, String password) throws SQLException;
}
