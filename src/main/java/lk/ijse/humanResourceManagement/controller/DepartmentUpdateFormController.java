package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.tm.DepartmentTm;
import lk.ijse.humanResourceManagement.model.DepartmentModel;

import java.sql.SQLException;

public class DepartmentUpdateFormController {
    @FXML
    private TextArea txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    private DepartmentModel depModel = new DepartmentModel();

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String desc = txtDesc.getText();

        DepartmentDto updatedDepartment = new DepartmentDto(id, name, desc);
        try {
            boolean isUpdated = depModel.updateDepartment(updatedDepartment);

            if (isUpdated) {
                Stage stage = (Stage) txtName.getScene().getWindow();
                stage.close();
                new Alert(Alert.AlertType.INFORMATION, "Department updated successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update department").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setDepartmentData(DepartmentTm department) {
        txtName.setText(department.getName());
        txtDesc.setText(department.getDesc());
        txtId.setText(department.getId());
    }
}
