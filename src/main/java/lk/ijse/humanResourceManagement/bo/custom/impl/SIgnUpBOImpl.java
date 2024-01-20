package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.SignUpBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.UserDAO;
import lk.ijse.humanResourceManagement.dto.SignUpDto;
import lk.ijse.humanResourceManagement.entity.User;

import java.sql.SQLException;

public class SIgnUpBOImpl implements SignUpBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.USER);
    @Override
    public boolean saveUser(SignUpDto dto) throws SQLException {
        return userDAO.save(new User(dto.getFirstName(),dto.getLastName(),dto.getPosition(),dto.getUserName(),dto.getConfirmPassword()));
    }
}
