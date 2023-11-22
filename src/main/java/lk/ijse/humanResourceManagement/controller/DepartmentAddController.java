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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;


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
        if(validateDepartment()) {
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


    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDesc.setText("");
    }
}
