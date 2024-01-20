package lk.ijse.humanResourceManagement.bo.custom;

import lk.ijse.humanResourceManagement.bo.SuperBO;
import lk.ijse.humanResourceManagement.dto.ReviewDto;

import java.sql.SQLException;
import java.util.List;

public interface ReviewBO extends SuperBO {
    boolean saveReview(ReviewDto dto) throws SQLException;

    List<ReviewDto> loadAllReviews() throws SQLException;

    ReviewDto searchReview(String id) throws SQLException;

    boolean updateReview(ReviewDto dto) throws SQLException;

    boolean deleteReview(String id) throws SQLException;
    String generateNextReviewId() throws SQLException;

}
