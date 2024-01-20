package lk.ijse.humanResourceManagement.bo.custom.impl;

import lk.ijse.humanResourceManagement.bo.custom.ReviewBO;
import lk.ijse.humanResourceManagement.dao.DAOFactory;
import lk.ijse.humanResourceManagement.dao.custom.ReviewDAO;
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.entity.Review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewBOImpl implements ReviewBO {
    ReviewDAO reviewDAO = (ReviewDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOTypes.REVIEW);
    @Override
    public boolean saveReview(ReviewDto dto) throws SQLException {
        return reviewDAO.save(new Review(dto.getReviewId(),dto.getEmpId(),dto.getComments(),dto.getRating(),dto.getDate()));
    }

    @Override
    public List<ReviewDto> loadAllReviews() throws SQLException {
        ArrayList<ReviewDto> reviewDtos = new ArrayList<>();
        List<Review> reviews = reviewDAO.loadAll();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewDto(review.getReviewId(),review.getEmpId(),review.getComments(),review.getRating(),review.getDate()));
        }
        return reviewDtos;
    }

    @Override
    public ReviewDto searchReview(String id) throws SQLException {
        Review review = reviewDAO.search(id);
        ReviewDto reviewDto = new ReviewDto(review.getReviewId(),review.getEmpId(),review.getComments(),review.getRating(),review.getDate());
        return reviewDto;
    }

    @Override
    public boolean updateReview(ReviewDto dto) throws SQLException {
        return reviewDAO.update(new Review(dto.getReviewId(),dto.getEmpId(),dto.getComments(),dto.getRating(),dto.getDate()));
    }

    @Override
    public boolean deleteReview(String id) throws SQLException {
        return reviewDAO.delete(id);
    }

    @Override
    public String generateNextReviewId() throws SQLException {
        return reviewDAO.generateId();
    }

}
