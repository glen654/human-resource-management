package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.ReviewBO;
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.dto.tm.ReviewTm;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ReviewUpdateFormController implements Initializable {
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

    ReviewBO reviewBO = (ReviewBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.REVIEW);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        loadRating();
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(validateReview()){
            String review_id = txtReviewId.getText();
            String emp_id = txtEmpId.getText();
            String comment = txtComments.getText();
            String rating = String.valueOf(cmbRating.getValue());
            LocalDate date = txtDate.getValue();

            ReviewDto updatedReview = new ReviewDto(review_id,emp_id,comment, rating,date);
            try {
                boolean isUpdated = reviewBO.updateReview(updatedReview);

                if (isUpdated) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    new Alert(Alert.AlertType.CONFIRMATION, "Review updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update review").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean validateReview(){
        String id = txtReviewId.getText();

        boolean isIDValidated = Pattern.matches("[R][0-9]{3,}", id);
        if (!isIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Review ID!").show();
            return false;
        }

        String emp_id = txtEmpId.getText();

        boolean isEmpIDValidated = Pattern.matches("[E][0-9]{3,}", emp_id);
        if (!isEmpIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }

        String comments = txtComments.getText();

        boolean isCommentsValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", comments);
        if (!isCommentsValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Comments!").show();
            return false;
        }


        String rating = cmbRating.getValue();

        boolean isRatingValidated = Pattern.matches("Average|Good|Excellent", rating);
        if (!isRatingValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Rating!").show();
            return false;
        }

        LocalDate date = txtDate.getValue();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStartDate = date.format(formatter);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid  Date!").show();
            return false;
        }

        return true;
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
