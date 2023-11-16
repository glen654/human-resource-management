package lk.ijse.humanResourceManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;

public class ReviewDetailController {
    @FXML
    private Label lblComment;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblRating;

    @FXML
    private Label lblReviewId;

    @FXML
    private Label lblRId;

    public void searchReviewDetail(ReviewDto reviewDto){
        lblRId.setText(reviewDto.getReviewId());
        lblReviewId.setText(reviewDto.getReviewId());
        lblEmpId.setText(reviewDto.getEmpId());
        lblComment.setText(reviewDto.getComments());
        lblRating.setText(reviewDto.getRating());
        lblDate.setText(String.valueOf(reviewDto.getDate()));
    }
}
