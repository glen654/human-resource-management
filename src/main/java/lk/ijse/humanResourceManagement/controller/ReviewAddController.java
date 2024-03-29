package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.bo.custom.ReviewBO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.ReviewDto;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ReviewAddController implements Initializable {
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

    ReviewBO reviewBO = (ReviewBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.REVIEW);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadRating();
        loadAllEmployeeIds();
        generateNextReviewId();
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
       if(validateReview()){
           String reviewId = txtReviewId.getText();
           String empId = cmbEmpId.getValue();
           String comments = txtComments.getText();
           String rating = cmbRating.getValue();
           LocalDate date = txtDateOfBirth.getValue();

           var dto = new ReviewDto(reviewId,empId,comments,rating,date);

           try {
               boolean isSaved = reviewBO.saveReview(dto);

               if(isSaved) {
                   clearFields();
                   new Alert(Alert.AlertType.CONFIRMATION, "Review Saved Successfully").show();
               }else{
                   new Alert(Alert.AlertType.ERROR,"Review Save Unsuccesfull").show();
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

        String emp_id = cmbEmpId.getValue();

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

        LocalDate date = txtDateOfBirth.getValue();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStartDate = date.format(formatter);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid  Date!").show();
            return false;
        }

        return true;
    }
    private void loadRating() {
        List<String> rating = Arrays.asList("Average", "Good", "Excellent");
        ObservableList<String> ratingList = FXCollections.observableArrayList(rating);
        cmbRating.setItems(ratingList);
    }

    private void loadAllEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = employeeBO.loadAllEmployee();

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
            String review_Id = reviewBO.generateNextReviewId();
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
