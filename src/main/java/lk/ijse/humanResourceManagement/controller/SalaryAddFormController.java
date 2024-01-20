package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.bo.custom.SalaryBO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.SalaryDto;


import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SalaryAddFormController implements Initializable {
    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtBasicSalary;

    @FXML
    private TextField txtDeduction;

    @FXML
    private TextField txtEpf;

    @FXML
    private TextField txtOverTime;

    @FXML
    private TextField txtSalaryId;

    SalaryBO salaryBO = (SalaryBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.SALARY);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        generateNextSalaryId();
        loadStatus();
        loadAllEmployeeIds();
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(validateSalary()){
            String compensation_id = txtSalaryId.getText();
            String emp_id = cmbEmpId.getValue();
            double salary = Double.parseDouble(txtBasicSalary.getText());
            String status = cmbStatus.getValue();
            double salaryDeduction = Double.parseDouble(txtDeduction.getText());
            double epf = Double.parseDouble(txtEpf.getText());
            double overTime = Double.parseDouble(txtOverTime.getText());

            var dto = new SalaryDto(compensation_id,emp_id,salary,status,salaryDeduction,epf,overTime);

            try {
                boolean isSaved = salaryBO.saveSalary(dto);

                if(isSaved){
                    clearFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Salary Saved").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Salary not saved").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        txtSalaryId.setText("");
        cmbEmpId.setValue(null);
        txtBasicSalary.setText("");
        txtDeduction.setText("");
        txtEpf.setText("");
        txtOverTime.setText("");
        cmbStatus.setValue(null);
    }

    private void generateNextSalaryId() {
        try {
            String salary_Id = salaryBO.generateNextSalaryId();
            txtSalaryId.setText(salary_Id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadStatus() {
        List<String> status = Arrays.asList("Approved","Not Approved");
        ObservableList<String> statusList = FXCollections.observableArrayList(status);
        cmbStatus.setItems(statusList);
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

    public boolean validateSalary() {
        String compensation_id = txtSalaryId.getText();
        boolean isSalaryIDValidated = Pattern.matches("[S][0-9]{3,}", compensation_id);
        if (!isSalaryIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Compensation ID!").show();
            return false;
        }

        String emp_id = cmbEmpId.getValue();
        boolean isEmployeeIdValidated = Pattern.matches("[E][0-9]{3,}", emp_id);
        if (!isEmployeeIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }

        try {
            double salary = Double.parseDouble(txtBasicSalary.getText());
            if (salary <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Salary!").show();
            return false;
        }

        String status = cmbStatus.getValue();
        boolean isStatusValidated = Pattern.matches("Approved|Not Approved", status);
        if (!isStatusValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Status!").show();
            return false;
        }

        try {
            double salaryDeduction = Double.parseDouble(txtDeduction.getText());
            if (salaryDeduction < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Deduction!").show();
            return false;
        }

        try {
            double epf = Double.parseDouble(txtEpf.getText());
            if (epf < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Epf!").show();
            return false;
        }

        try {
            double overTime = Double.parseDouble(txtOverTime.getText());
            if (overTime < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid OverTime!").show();
            return false;
        }

        return true;
    }

}
