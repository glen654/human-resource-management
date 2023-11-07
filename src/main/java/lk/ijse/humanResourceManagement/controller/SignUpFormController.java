package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.SignUpDto;
import lk.ijse.humanResourceManagement.model.SignUpModel;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFormController {
    public TextField txtId;
    public TextField txtUsername;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public TextField txtPosition;
    public AnchorPane rootNode;

    private SignUpModel signUpModel = new SignUpModel();
    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String id = txtId.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String position = txtPosition.getText();

        var dto = new SignUpDto(id, position, userName, password);

        try {
            boolean isSaved = signUpModel.saveUser(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully Created An Account!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtPosition.setText("");

    }

    public void btnSignInPageOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SignIn Form");
    }
}
