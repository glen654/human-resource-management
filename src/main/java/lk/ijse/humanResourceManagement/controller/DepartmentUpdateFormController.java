package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.DepartmentBO;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.tm.DepartmentTm;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class DepartmentUpdateFormController {
    @FXML
    private TextArea txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    DepartmentBO departmentBO = (DepartmentBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.DEPARTMENT);
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       if(validateDepartment()){
           String id = txtId.getText();
           String name = txtName.getText();
           String desc = txtDesc.getText();

           DepartmentDto updatedDepartment = new DepartmentDto(id, name, desc);
           try {
               boolean isUpdated = departmentBO.updateDepartment(updatedDepartment);

               if (isUpdated) {
                   Stage stage = (Stage) txtName.getScene().getWindow();
                   stage.close();
                   new Alert(Alert.AlertType.CONFIRMATION, "Department updated successfully").show();
               } else {
                   new Alert(Alert.AlertType.ERROR, "Failed to update department").show();
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }

    }

    public boolean validateDepartment(){
        String id = txtId.getText();

        boolean isDepIDValidated = Pattern.matches("[D][0-9]{3,}", id);
        if (!isDepIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Department ID!").show();
            return false;
        }

        String name = txtName.getText();

        boolean isNameValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", name);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Department Name!").show();
            return false;
        }

        String description = txtDesc.getText();

        boolean isDescriptionValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", description);
        if (!isDescriptionValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description!").show();
            return false;
        }


        return true;
    }


    public void setDepartmentData(DepartmentTm department) {
        txtName.setText(department.getName());
        txtDesc.setText(department.getDesc());
        txtId.setText(department.getId());
    }
}
