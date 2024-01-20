package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;

import java.sql.SQLException;
import java.util.List;

public interface RequestBO extends SuperBO {
    boolean saveLeaveRequest(LeaveRequestDto dto) throws SQLException;

    String generateNextRequestId() throws SQLException;

    List<LeaveRequestDto> loadAllRequest() throws SQLException;

    LeaveRequestDto searchRequest(String id) throws SQLException;

    boolean updateRequest(LeaveRequestDto dto) throws SQLException;

    boolean deleteRequest(String id) throws SQLException;

    int getRequestCount() throws SQLException;
}
