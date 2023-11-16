package lk.ijse.humanResourceManagement.controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.model.EmployeeModel;
import lk.ijse.humanResourceManagement.model.ReviewModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReviewAddController {
    @FXML
    private ComboBox<String> cmbRating;

    @FXML
    private TextArea txtComments;

    @FXML
    private DatePicker txtDateOfBirth;

    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private TextField txtReviewId;

    private ReviewModel reviewModel = new ReviewModel();
    private EmployeeModel empModel = new EmployeeModel();
    public void initialize(){
        loadRating();
        loadAllEmployeeIds();
        generateNextReviewId();
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String reviewId = txtReviewId.getText();
        String empId = cmbEmpId.getValue();
        String comments = txtComments.getText();
        String rating = cmbRating.getValue();
        LocalDate date = txtDateOfBirth.getValue();

        var dto = new ReviewDto(reviewId,empId,comments,rating,date);

        try {
            boolean isSaved = reviewModel.saveReview(dto);

            if(isSaved) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Review Saved Successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRating() {
        List<String> rating = Arrays.asList("Average", "Good", "Excellent");
        ObservableList<String> ratingList = FXCollections.observableArrayList(rating);
        cmbRating.setItems(ratingList);
    }

    private void loadAllEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = empModel.loadAllEmployee();

            for (EmployeeDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextReviewId() {
        try {
            String review_Id = reviewModel.generateNextReviewId();
            txtReviewId.setText(review_Id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {
        txtReviewId.setText("");
        cmbRating.setValue("");
        cmbEmpId.setValue("");
        txtComments.setText("");
        txtDateOfBirth.setValue(null);
    }
}
