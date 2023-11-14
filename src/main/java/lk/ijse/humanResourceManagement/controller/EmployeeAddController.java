package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.model.DepartmentModel;
import lk.ijse.humanResourceManagement.model.EmployeeModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SimpleTimeZone;

public class EmployeeAddController {
    @FXML
    private ComboBox<String> cmbDepartmentIds;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker txtDateOfBirth;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtJobRole;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextArea txtQualification;

    @FXML
    private TextField txtSalary;
    private EmployeeModel empModel = new EmployeeModel();
   
    private DepartmentModel depModel = new DepartmentModel();
    
    public void initialize(){
        loadAllDepartmentIds();
        loadGenders();
        generateNextEmployeeId();
    }

    private void generateNextEmployeeId() {
        try {
            String empId = empModel.generateNextOrderId();
            txtId.setText(empId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadGenders() {
        List<String> genders = Arrays.asList("Male", "Female");
        ObservableList<String> genderList = FXCollections.observableArrayList(genders);
        cmbGender.setItems(genderList);
    }

    private void loadAllDepartmentIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<DepartmentDto> idList = depModel.loadAllDepartments();

            for (DepartmentDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbDepartmentIds.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML 
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        int contact = Integer.parseInt(txtContact.getText());
        String qualification = txtQualification.getText();
        String history = txtHistory.getText();
        String depId = cmbDepartmentIds.getValue();
        LocalDate dob =txtDateOfBirth.getValue();
        String dobString = dob != null ? dob.toString() : null;
        String gender = cmbGender.getValue();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String role = txtJobRole.getText();

        var dto = new EmployeeDto(id, firstName, lastName, contact,qualification,history,depId,dob,gender,email,salary,role);

        try {
            boolean isSaved = empModel.saveEmployee(dto);

            if (isSaved) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Successfully!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtContact.setText("");
        txtDateOfBirth.setValue(null);
        txtJobRole.setText("");
        txtEmail.setText("");
        txtHistory.setText("");
        txtQualification.setText("");
        txtSalary.setText("");
        cmbDepartmentIds.setValue(null);
        cmbGender.setValue(null);
    }
}
