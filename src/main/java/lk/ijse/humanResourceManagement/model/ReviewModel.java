package lk.ijse.humanResourceManagement.model;

import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReviewModel {
    public boolean saveReview(ReviewDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO performanceReview VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getReviewId());
        pstm.setString(2, dto.getEmpId());
        pstm.setString(3, dto.getComments());
        pstm.setString(4, dto.getRating());
        pstm.setString(5, String.valueOf(dto.getDate()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<ReviewDto> loadAllReviews() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM performanceReview";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ReviewDto> reviewList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            reviewList.add(new ReviewDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate()
            ));
        }

        return reviewList;
    }

    public ReviewDto searchReview(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM performanceReview WHERE review_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        ReviewDto dto = null;

        if(resultSet.next()) {
            String review_id = resultSet.getString(1);
            String emp_id = resultSet.getString(2);
            String comments = resultSet.getString(3);
            String rating = resultSet.getString(4);
            LocalDate date = resultSet.getDate(5).toLocalDate();

            dto = new ReviewDto(review_id,emp_id,comments,rating,date);
        }
        return dto;
    }

    public boolean updateReview(ReviewDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE performanceReview SET comments = ?, ratings = ?, reviewDate = ? WHERE review_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getComments());
        pstm.setString(2,dto.getRating());
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4,dto.getReviewId());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteReview(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM performanceReview WHERE review_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

    public String generateNextReviewId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT review_id FROM performanceReview ORDER BY review_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitReviewId(resultSet.getString(1));
        }
        return splitReviewId(null);
    }

    private String splitReviewId(String currentReviewId) {
        if(currentReviewId != null) {
            String[] split = currentReviewId.split("R0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "R00" + id;
        } else {
            return "R001";
        }
    }
}
