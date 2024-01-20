package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.LoginBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.UserDAO;
import lk.ijse.humanResourceManagement.dto.LoginDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.USER);
    @Override
    public LoginDto searchUser(String userName, String password) throws SQLException {
       return userDAO.searchUser(userName,password);

    }
}
