package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.RequestDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;
import lk.ijse.humanResourceManagement.entity.LeaveRequest;
import lk.ijse.humanResourceManagement.entity.Program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {
    @Override
    public boolean save(LeaveRequest entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO leaveRequest (request_id,emp_id,leaveType,startDate,endDate,status,requestDate) VALUES (?,?,?,?,?,?,?)",
                        entity.getRequest_id(),entity.getEmp_id(),entity.getLeaveType(),entity.getStartDate(),entity.getEndDate(),entity.getStatus(),entity.getRequestDate());
    }

    @Override
    public String generateId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT request_id FROM leaveRequest ORDER BY request_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("L0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "L00" + id;
        } else {
            return "L001";
        }
    }

    @Override
    public List<LeaveRequest> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM leaveRequest");
        ArrayList<LeaveRequest> getAllRequest = new ArrayList<>();
        while (rst.next()){
            LeaveRequest leaveRequest = new LeaveRequest(rst.getString("request_id"), rst.getString("emp_id"), rst.getString("leaveType"), rst.getDate("startDate").toLocalDate(), rst.getDate("endDate").toLocalDate(),rst.getString("status"), rst.getDate("requestDate").toLocalDate());
            getAllRequest.add(leaveRequest);
        }
        return getAllRequest;
    }

    @Override
    public LeaveRequest search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM leaveRequest WHERE request_id=?",id);
        rst.next();
        return new LeaveRequest(rst.getString("request_id"), rst.getString("emp_id"), rst.getString("leaveType"), rst.getDate("startDate").toLocalDate(), rst.getDate("endDate").toLocalDate(),rst.getString("status"), rst.getDate("requestDate").toLocalDate());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM leaveRequest WHERE request_id=?",id);
    }

    @Override
    public boolean update(LeaveRequest entity) throws SQLException {
        return SQLUtil.execute("UPDATE leaveRequest SET emp_id = ?, leaveType = ?, startDate = ?, endDate = ?, status = ?, requestDate = ? WHERE request_id = ?",
                entity.getEmp_id(),entity.getLeaveType(),entity.getStartDate(),entity.getEndDate(),entity.getStatus(),entity.getRequestDate(),entity.getRequest_id());
    }

    @Override
    public int getCount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM leaveRequest" );
        if(resultSet.next()){
            return resultSet.getInt(1);
        }

        return 0;

    }

}
