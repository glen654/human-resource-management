package lk.ijse.humanResourceManagement.dao.custom.impl;

import lk.ijse.humanResourceManagement.dao.SQLUtil;
import lk.ijse.humanResourceManagement.dao.custom.ReviewDAO;
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.entity.Program;
import lk.ijse.humanResourceManagement.entity.Review;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {
    @Override
    public boolean save(Review entity) throws SQLException {
        return SQLUtil.
                execute("INSERT INTO performanceReview (review_id,emp_id,comments,ratings,reviewDate) VALUES (?,?,?,?,?)",
                        entity.getReviewId(),entity.getEmpId(),entity.getComments(),entity.getRating(),entity.getDate());
    }

    @Override
    public String generateId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT review_id FROM performanceReview ORDER BY review_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("R0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "R00" + id;
        } else {
            return "R001";
        }
    }

    @Override
    public List<Review> loadAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM performanceReview");
        ArrayList<Review> getAllReview = new ArrayList<>();
        while (rst.next()){
            Review review = new Review(rst.getString("review_id"), rst.getString("emp_id"), rst.getString("comments"),rst.getString("ratings"), rst.getDate("reviewDate").toLocalDate());
            getAllReview.add(review);
        }
        return getAllReview;
    }

    @Override
    public Review search(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM performanceReview WHERE review_id=?",id);
        rst.next();
        return new Review(rst.getString("review_id"),rst.getString("emp_id"), rst.getString("comments"),rst.getString("ratings"), rst.getDate("reviewDate").toLocalDate());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM performanceReview WHERE review_id=?",id);
    }

    @Override
    public boolean update(Review entity) throws SQLException {
        return SQLUtil.execute("UPDATE performanceReview SET comments = ?, ratings = ?, reviewDate = ? WHERE review_id = ?",
                entity.getComments(),entity.getRating(),entity.getDate(),entity.getReviewId());
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }
}
