package lk.ijse.humanResourceManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;

public class EmployeeDetailsFormController {
    @FXML
    private Label lblBirthDay;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblDepartment;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblHistory;

    @FXML
    private Label lblId;

    @FXML
    private Label lblJobRole;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblName;

    @FXML
    private Label lblQualification;

    @FXML
    private Label lblSalary;


    public void searchEmployeeDetails(EmployeeDto employeeDto) {
        lblName.setText(employeeDto.getFirstName() + " " + employeeDto.getLastName());
        lblId.setText(employeeDto.getId());
        lblFirstName.setText(employeeDto.getFirstName());
        lblLastName.setText(employeeDto.getLastName());
        lblBirthDay.setText(String.valueOf(employeeDto.getDateOfBirth()));
        lblContact.setText(String.valueOf(employeeDto.getContact()));
        lblDepartment.setText(employeeDto.getDepId());
        lblEmail.setText(employeeDto.getEmail());
        lblGender.setText(employeeDto.getGender());
        lblHistory.setText(employeeDto.getHistory());
        lblJobRole.setText(employeeDto.getJobRole());
        lblQualification.setText(employeeDto.getQualification());
        lblSalary.setText(String.valueOf(employeeDto.getSalary()));
    }
}
