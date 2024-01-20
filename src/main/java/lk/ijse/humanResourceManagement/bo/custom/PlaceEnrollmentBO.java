package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.ProgramDto;

import java.sql.SQLException;

public interface PlaceEnrollmentBO extends SuperBO {
    boolean placeEnrollment(ProgramDto programDto) throws SQLException;
}
