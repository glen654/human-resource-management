package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.tm.EmployeeTm;
import lk.ijse.humanResourceManagement.model.EmployeeModel;

import java.sql.SQLException;

public class EmployeeUpdateFormController {
    @FXML
    private Label lblName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtHistory;

    @FXML
    private TextField txtJobRole;

    @FXML
    private TextField txtQualification;

    @FXML
    private TextField txtSalary;

    private EmployeeModel empModel = new EmployeeModel();
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        int contact = Integer.parseInt(txtContact.getText());
        String email = txtEmail.getText();
        String history = txtHistory.getText();
        String jobRole = txtJobRole.getText();
        String qualification = txtQualification.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        EmployeeDto updatedEmployee = new EmployeeDto(contact,email, history,jobRole,qualification,salary);
        try {
            boolean isUpdated = empModel.updateEmployee(updatedEmployee);

            if (isUpdated) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Employee").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setEmployeeData(EmployeeTm employee) {
       lblName.setText(employee.getFirstName());
       txtContact.setText(String.valueOf(employee.getContact()));
       txtEmail.setText(employee.getEmail());
       txtHistory.setText(employee.getHistory());
       txtJobRole.setText(employee.getJobRole());
       txtQualification.setText(employee.getQualification());
       txtSalary.setText(String.valueOf(employee.getSalary()));

    }


}
