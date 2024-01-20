package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.ProgramDto;

import java.sql.SQLException;
import java.util.List;

public interface ProgramBO extends SuperBO {
    boolean saveProgram(ProgramDto dto) throws SQLException;

    String generateNextProgramId() throws SQLException;

    List<ProgramDto> loadAllPrograms() throws SQLException;

    ProgramDto searchProgram(String id) throws SQLException;

    boolean updateProgram(ProgramDto dto) throws SQLException;

    boolean deleteProgram(String id) throws SQLException;

    int getProgramCount() throws SQLException;
}
