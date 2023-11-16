package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.dto.tm.ReviewTm;
import lk.ijse.humanResourceManagement.model.ReviewModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReviewUpdateFormController {
    public DatePicker txtDate;

    @FXML
    private Label lblReviewId;

    @FXML
    private ComboBox<String> cmbRating;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextArea txtComments;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtReviewId;

    private ReviewModel reviewModel = new ReviewModel();

    public void initialize(){
        loadRating();
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String review_id = txtReviewId.getText();
        String emp_id = txtEmpId.getText();
        String comment = txtComments.getText();
        String rating = String.valueOf(cmbRating.getValue());
        LocalDate date = txtDate.getValue();

        ReviewDto updatedReview = new ReviewDto(review_id,emp_id,comment, rating,date);
        try {
            boolean isUpdated = reviewModel.updateReview(updatedReview);

            if (isUpdated) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                new Alert(Alert.AlertType.INFORMATION, "Review updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update review").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setReviewData(ReviewTm review) {
        lblReviewId.setText(review.getReviewId());
        txtReviewId.setText(review.getReviewId());
        txtEmpId.setText(review.getEmpId());
        txtComments.setText(review.getComments());
        cmbRating.getSelectionModel().select(review.getRating());
        txtDate.setValue(review.getDate());
    }

    private void loadRating() {
        List<String> rating = Arrays.asList("Average", "Good", "Excellent");
        ObservableList<String> ratingList = FXCollections.observableArrayList(rating);
        cmbRating.setItems(ratingList);
    }
}
