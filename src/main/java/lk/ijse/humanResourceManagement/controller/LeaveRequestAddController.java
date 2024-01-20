package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.bo.custom.RequestBO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LeaveRequestAddController implements Initializable {
    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private ComboBox<String> cmbLeaveType;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private DatePicker txtEndDate;

    @FXML
    private DatePicker txtRequestDate;

    @FXML
    private TextField txtRequestId;

    @FXML
    private DatePicker txtStartDate;

    RequestBO requestBO = (RequestBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.REQUEST);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadStatus();
        loadType();
        loadAllEmployeeIds();
        generateNextRequestId();
    }

    private void generateNextRequestId() {
        try {
            String request_Id = requestBO.generateNextRequestId();
            txtRequestId.setText(request_Id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(validateRequest()){
            String request_id = txtRequestId.getText();
            String emp_id = cmbEmpId.getValue();
            String leaveType = cmbLeaveType.getValue();
            LocalDate startDate = txtStartDate.getValue();
            LocalDate endDate = txtEndDate.getValue();
            String status = cmbStatus.getValue();
            LocalDate requestDate = txtRequestDate.getValue();

            var dto = new LeaveRequestDto(request_id,emp_id,leaveType,startDate,endDate,status,requestDate);

            try {
                boolean isSaved = requestBO.saveLeaveRequest(dto);

                if(isSaved){
                    clearFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Leave Request Saved!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error Saving Leave Request").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean validateRequest(){
        String request_id = txtRequestId.getText();

        boolean isRequestIDValidated = Pattern.matches("[L][0-9]{3,}", request_id);
        if (!isRequestIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Request ID!").show();
            return false;
        }

        String emp_id = cmbEmpId.getValue();

        boolean isEmpIDValidated = Pattern.matches("[E][0-9]{3,}", emp_id);
        if (!isEmpIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }

        String leaveType = cmbLeaveType.getValue();

        boolean isLeaveTypeValidated = Pattern.matches("Privilege Leave|Casual Leave|Sick Leave|Maternity Leave|Compensatory Off|Marriage Leave|Paternity Leave|Bereavement Leave|Leave Without Pay", leaveType);
        if (!isLeaveTypeValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Leave Type!").show();
            return false;
        }

        LocalDate startDate = txtStartDate.getValue();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStartDate = startDate.format(formatter);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Start Date!").show();
            return false;
        }

        LocalDate endDate = txtEndDate.getValue();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedEndDate = endDate.format(formatter);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid End Date!").show();
            return false;
        }

        String status = cmbStatus.getValue();

        boolean isStatusValidated = Pattern.matches("Approved|Not Approved", status);
        if (!isStatusValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Status!").show();
            return false;
        }

        LocalDate requestDate = txtRequestDate.getValue();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedEndDate = requestDate.format(formatter);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Request Date!").show();
            return false;
        }

        return true;
    }

    private void loadStatus() {
        List<String> status = Arrays.asList("Approved", "Not Approved");
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

    private void loadType() {
        List<String> type = Arrays.asList("Privilege Leave", "Casual Leave", "Sick Leave", "Maternity Leave", "Compensatory Off", "Marriage Leave", "Paternity Leave","Bereavement Leave","Leave Without Pay");
        ObservableList<String> typeList = FXCollections.observableArrayList(type);
        cmbLeaveType.setItems(typeList);
    }

    public void clearFields(){
        txtRequestId.setText("");
        cmbEmpId.setValue("");
        cmbStatus.setValue("");
        cmbLeaveType.setValue("");
        txtStartDate.setValue(null);
        txtEndDate.setValue(null);
        txtRequestDate.setValue(null);
    }
}
