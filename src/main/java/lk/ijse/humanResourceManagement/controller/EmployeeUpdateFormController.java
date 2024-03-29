package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.DepartmentBO;
import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.tm.EmployeeTm;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeUpdateFormController implements Initializable {
    @FXML
    private ComboBox<String> cmbDepatment;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private Label lblName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtHistory;

    @FXML
    private TextField txtJobRole;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtQualification;

    @FXML
    private TextField txtSalary;

    @FXML
    private DatePicker txtDateOfBirth;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.EMPLOYEE);
    DepartmentBO departmentBO = (DepartmentBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.DEPARTMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadGenders();
        loadAllDepartmentIds();
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        int contact = Integer.parseInt(txtContact.getText());
        String qualification = txtQualification.getText();
        String history = txtHistory.getText();
        String depId = cmbDepatment.getValue();
        LocalDate dob =txtDateOfBirth.getValue();
        String dobString = dob != null ? dob.toString() : null;
        String gender = cmbGender.getValue();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String role = txtJobRole.getText();

        EmployeeDto updatedEmployee = new EmployeeDto(id,firstName,lastName,contact,qualification,history,depId,dob,gender,email,salary,role);
        try {
            boolean isUpdated = employeeBO.updateEmployee(updatedEmployee);

            if (isUpdated) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Employee").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setEmployeeData(EmployeeTm employee) {
        if(employee != null) {
            lblName.setText(employee.getFirstName());
            txtEmpId.setText(employee.getId());
            txtFirstName.setText(employee.getFirstName());
            txtLastName.setText(employee.getLastName());
            txtDateOfBirth.setValue(employee.getDateOfBirth());
            cmbDepatment.setValue(employee.getDepId());
            cmbGender.setValue(employee.getGender());
            txtContact.setText(String.valueOf(employee.getContact()));
            txtEmail.setText(employee.getEmail());
            txtHistory.setText(employee.getHistory());
            txtJobRole.setText(employee.getJobRole());
            txtQualification.setText(employee.getQualification());
            txtSalary.setText(String.valueOf(employee.getSalary()));
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
            List<DepartmentDto> idList = departmentBO.loadAllDepartments();

            for (DepartmentDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbDepatment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
