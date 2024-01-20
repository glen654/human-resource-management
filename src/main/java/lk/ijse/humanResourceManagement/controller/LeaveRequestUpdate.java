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
import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.bo.custom.RequestBO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;
import lk.ijse.humanResourceManagement.dto.tm.LeaveRequestTm;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static javafx.fxml.FXMLLoader.loadType;

public class LeaveRequestUpdate implements Initializable {
    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private ComboBox<String> cmbLeaveType;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private Label lblEmpId;

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

    private void loadStatus() {
        List<String> status = Arrays.asList("Approved", "Not Approved");
        ObservableList<String> statusList = FXCollections.observableArrayList(status);
        cmbStatus.setItems(statusList);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(validateRequest()){
            String request_id = txtRequestId.getText();
            String emp_id = cmbEmpId.getValue();
            String leaveType = cmbLeaveType.getValue();
            LocalDate startDate = txtStartDate.getValue();
            LocalDate endDate = txtEndDate.getValue();
            String status = cmbStatus.getValue();
            LocalDate requestDate = txtRequestDate.getValue();

            LeaveRequestDto updatedRequest = new LeaveRequestDto(request_id,emp_id,leaveType,startDate,endDate,status,requestDate);

            try {
                boolean isUpdated = requestBO.updateRequest(updatedRequest);

                if (isUpdated) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                    new Alert(Alert.AlertType.INFORMATION, "Request updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update request").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setRequestData(LeaveRequestTm request) {
        lblEmpId.setText(request.getEmp_id());
        txtRequestId.setText(request.getRequest_id());
        cmbEmpId.setValue(request.getEmp_id());
        txtStartDate.setValue(request.getStartDate());
        txtEndDate.setValue(request.getEndDate());
        cmbStatus.setValue(request.getStatus());
        txtRequestDate.setValue(request.getRequestDate());
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

}
