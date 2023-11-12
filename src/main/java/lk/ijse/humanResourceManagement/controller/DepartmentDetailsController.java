package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.model.DepartmentModel;

import java.io.IOException;
import java.sql.SQLException;

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
