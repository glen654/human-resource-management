package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.ProgramBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.ProgramDAO;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.entity.Program;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.PROGRAM);
    @Override
    public boolean saveProgram(ProgramDto dto) throws SQLException {
        return programDAO.save(new Program(dto.getProgram_id(),dto.getName(),dto.getDescription(),dto.getTrainers(),dto.getDuration(),dto.getEmp_id()));
    }

    @Override
    public String generateNextProgramId() throws SQLException {
        return programDAO.generateId();
    }


    @Override
    public List<ProgramDto> loadAllPrograms() throws SQLException {
        ArrayList<ProgramDto> programDtos = new ArrayList<>();
        List<Program> programs = programDAO.loadAll();
        for (Program program : programs) {
            programDtos.add(new ProgramDto(program.getProgram_id(),program.getName(),program.getDescription(),program.getTrainers(),program.getDuration(),program.getEmp_id()));
        }
        return programDtos;
    }

    @Override
    public ProgramDto searchProgram(String id) throws SQLException {
        Program program = programDAO.search(id);
        ProgramDto programDto = new ProgramDto(program.getProgram_id(),program.getName(),program.getDescription(),program.getTrainers(),program.getDuration(),program.getEmp_id());
        return programDto;
    }

    @Override
    public boolean updateProgram(ProgramDto dto) throws SQLException {
        return programDAO.update(new Program(dto.getProgram_id(),dto.getName(),dto.getDescription(),dto.getTrainers(),dto.getDuration(),dto.getEmp_id()));
    }

    @Override
    public boolean deleteProgram(String id) throws SQLException {
        return programDAO.delete(id);
    }

    @Override
    public int getProgramCount() throws SQLException {
        return programDAO.getCount();
    }
}
