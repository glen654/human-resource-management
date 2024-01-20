package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.UserDAO;
import lk.ijse.humanResourceManagement.dto.LoginDto;
import lk.ijse.humanResourceManagement.entity.Login;
import lk.ijse.humanResourceManagement.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean save(User entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO user (first_name,last_name,position,userName,password) VALUES (?,?,?,?,?)",
                        entity.getFirstName(),entity.getLastName(),entity.getPosition(),entity.getUserName(),entity.getConfirmPassword());
    }

    @Override
    public String generateId() throws SQLException {
        return null;
    }

    @Override
    public String splitId(String currentId) {
        return null;
    }

    @Override
    public List<User> loadAll() throws SQLException {
        return null;
    }

    @Override
    public User search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return false;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }


    @Override
    public LoginDto searchUser(String userName, String password) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE userName = ? AND password = ?",userName,password);
        if(resultSet.next()){
            LoginDto loginDto = new LoginDto();
            loginDto.setUserName(resultSet.getString("userName"));
            loginDto.setPassword(resultSet.getString("password"));

            return loginDto;
        }else {
            return null;
        }
    }
}
