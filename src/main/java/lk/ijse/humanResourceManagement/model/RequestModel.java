package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestModel {
    public boolean saveLeaveRequest(LeaveRequestDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO leaveRequest VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getRequest_id());
        pstm.setString(2, dto.getEmp_id());
        pstm.setString(3, dto.getLeaveType());
        pstm.setString(4, String.valueOf(dto.getStartDate()));
        pstm.setString(5, String.valueOf(dto.getEndDate()));
        pstm.setString(6, dto.getStatus());
        pstm.setString(7, String.valueOf(dto.getRequestDate()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public String generateNextRequestId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT request_id FROM leaveRequest ORDER BY request_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitRequestId(resultSet.getString(1));
        }
        return splitRequestId(null);
    }

    private String splitRequestId(String currentRequestId) {
        if(currentRequestId != null) {
            String[] split = currentRequestId.split("L0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "L00" + id;
        } else {
            return "L001";
        }
    }

    public List<LeaveRequestDto> loadAllRequest() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM leaveRequest";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<LeaveRequestDto> requestList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            requestList.add(new LeaveRequestDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(6),
                    resultSet.getDate(7).toLocalDate()
            ));
        }

        return requestList;
    }

    public LeaveRequestDto searchRequest(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM leaveRequest WHERE request_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        LeaveRequestDto dto = null;

        if(resultSet.next()) {
            String request_id = resultSet.getString(1);
            String emp_id = resultSet.getString(2);
            String leaveType = resultSet.getString(3);
            LocalDate startDate = resultSet.getDate(4).toLocalDate();
            LocalDate endDate = resultSet.getDate(5).toLocalDate();
            String status = resultSet.getString(6);
            LocalDate requestDate = resultSet.getDate(7).toLocalDate();

            dto = new LeaveRequestDto(request_id,emp_id,leaveType,startDate,endDate,status,requestDate);
        }
        return dto;
    }

    public boolean updateRequest(LeaveRequestDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE leaveRequest SET emp_id = ?, leaveType = ?, startDate = ?, endDate = ?, status = ?, requestDate = ? WHERE request_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getEmp_id());
        pstm.setString(2,dto.getLeaveType());
        pstm.setString(3, String.valueOf(dto.getStartDate()));
        pstm.setString(4, String.valueOf(dto.getEndDate()));
        pstm.setString(5,dto.getStatus());
        pstm.setString(6, String.valueOf(dto.getRequestDate()));
        pstm.setString(7,dto.getRequest_id());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteRequest(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM leaveRequest WHERE request_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }
}
