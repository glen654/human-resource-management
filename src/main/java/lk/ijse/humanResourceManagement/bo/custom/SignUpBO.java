package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.SignUpDto;

import java.sql.SQLException;

public interface SignUpBO  extends SuperBO {
    boolean saveUser(SignUpDto dto) throws SQLException;
}
