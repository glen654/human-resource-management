package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EmployeeAddController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker txtDateOfBirth;

    @FXML
    private ComboBox<?> txtDepartment;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private ComboBox<?> txtGender;

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextArea txtQualification;

    @FXML
    private TextField txtSalary;
    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }
}
