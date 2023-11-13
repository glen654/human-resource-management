package lk.ijse.humanResourceManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;


public class DepartmentDetailsController{

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblId;

    @FXML
    private Label lblName;


    public void searchDepartmentDetails(DepartmentDto departmentDto){
        lblId.setText(departmentDto.getId());
        lblName.setText(departmentDto.getName());
        lblDesc.setText(departmentDto.getDesc());
    }

}
