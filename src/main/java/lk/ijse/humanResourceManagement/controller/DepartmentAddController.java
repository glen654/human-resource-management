package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.tm.DepartmentTm;
import lk.ijse.humanResourceManagement.model.DepartmentModel;

import java.sql.SQLException;


public class DepartmentAddController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextArea txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    private DepartmentModel deModel = new DepartmentModel();

    @FXML
    void btnSaveOnAction(ActionEvent event){
        String id = txtId.getText();
        String name = txtName.getText();
        String desc = txtDesc.getText();

        var dto = new DepartmentDto(id,name,desc);

        try {
            boolean isSaved = deModel.saveDepartment(dto);

            if(isSaved){
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION,"New Department Saved Successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDepartmentData(DepartmentTm department) {
        // Set the data in the text fields
        txtName.setText(department.getName());
        txtDesc.setText(department.getDesc());
        txtId.setText(department.getId());
        // Set the department ID

    }
    public void handleUpdateAction(ActionEvent event){

        try {
            // Create a DepartmentDto with updated data
            DepartmentDto updatedDto = new DepartmentDto();
            updatedDto.setId(txtId.getText());
            updatedDto.setName(txtName.getText());
            updatedDto.setDesc(txtDesc.getText());

            // Update the department in the database
            boolean updated = deModel.updateDepartment(updatedDto);

            if (updated) {
                btnSaveOnAction(event);
                // Optionally, you can close the update form or show a success message
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
                // Add any additional logic you need after a successful update
            } else {
                // Handle the case where the update fails
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (SQLException e) {
            // Handle the exception more gracefully, e.g., show an error message
            e.printStackTrace();
        }
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDesc.setText("");
    }
}
