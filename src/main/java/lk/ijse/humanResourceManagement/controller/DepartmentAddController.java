package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
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

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDesc.setText("");
    }
}
