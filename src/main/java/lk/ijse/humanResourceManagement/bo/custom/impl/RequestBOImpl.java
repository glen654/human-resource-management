package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.RequestBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.RequestDAO;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;
import lk.ijse.humanResourceManagement.entity.LeaveRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestBOImpl implements RequestBO{
    RequestDAO requestDAO = (RequestDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.REQUEST);
    @Override
    public boolean saveLeaveRequest(LeaveRequestDto dto) throws SQLException {
        return requestDAO.save(new LeaveRequest(dto.getRequest_id(),dto.getEmp_id(),dto.getLeaveType(),dto.getStartDate(),dto.getEndDate(),dto.getStatus(),dto.getRequestDate()));
    }

    @Override
    public String generateNextRequestId() throws SQLException {
        return requestDAO.generateId();
    }


    @Override
    public List<LeaveRequestDto> loadAllRequest() throws SQLException {
        ArrayList<LeaveRequestDto> requestDtos = new ArrayList<>();
        List<LeaveRequest> requests = requestDAO.loadAll();
        for (LeaveRequest request : requests) {
            requestDtos.add(new LeaveRequestDto(request.getRequest_id(),request.getEmp_id(),request.getLeaveType(),request.getStartDate(),request.getEndDate(),request.getStatus(),request.getRequestDate()));
        }
        return requestDtos;
    }

    @Override
    public LeaveRequestDto searchRequest(String id) throws SQLException {
        LeaveRequest request = requestDAO.search(id);
        LeaveRequestDto requestDto = new LeaveRequestDto(request.getRequest_id(),request.getEmp_id(),request.getLeaveType(),request.getStartDate(),request.getEndDate(),request.getStatus(),request.getRequestDate());
        return requestDto;
    }

    @Override
    public boolean updateRequest(LeaveRequestDto dto) throws SQLException {
        return requestDAO.update(new LeaveRequest(dto.getRequest_id(),dto.getEmp_id(),dto.getLeaveType(),dto.getStartDate(),dto.getEndDate(),dto.getStatus(),dto.getRequestDate()));
    }

    @Override
    public boolean deleteRequest(String id) throws SQLException {
        return requestDAO.delete(id);
    }

    @Override
    public int getRequestCount() throws SQLException {
        return requestDAO.getCount();
    }
}
