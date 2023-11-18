package lk.ijse.humanResourceManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;

public class LeaveRequestDetailsController {
    @FXML
    private Label lblEmpId;

    @FXML
    private Label lblEndDate;

    @FXML
    private Label lblLeaveType;

    @FXML
    private Label lblRequestDate;

    @FXML
    private Label lblRequestId;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lbltxtEmpId;

    @FXML
    private AnchorPane rootNode;

    public void searchRequestDetail(LeaveRequestDto requestDto) {
        lbltxtEmpId.setText(requestDto.getEmp_id());
        lblRequestId.setText(requestDto.getRequest_id());
        lblEmpId.setText(requestDto.getEmp_id());
        lblLeaveType.setText(requestDto.getLeaveType());
        lblStartDate.setText(String.valueOf(requestDto.getStartDate()));
        lblEndDate.setText(String.valueOf(requestDto.getEndDate()));
        lblStatus.setText(requestDto.getStatus());
        lblRequestDate.setText(String.valueOf(requestDto.getRequestDate()));
    }
}
