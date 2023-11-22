package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.SignUpDto;
import lk.ijse.humanResourceManagement.model.SignUpModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignUpFormController {
  
    public AnchorPane rootNode;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtPosition;
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public TextField txtUsername;

    private SignUpModel signUpModel = new SignUpModel();
    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        if(validateUser()){
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String position = txtPosition.getText();
            String userName = txtUsername.getText();
            String password = txtConfirmPassword.getText();


            var dto = new SignUpDto(firstName, lastName, position, userName,password);

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
    }

    public boolean validateUser(){
        String firstName = txtFirstName.getText();

        boolean isFirstNameValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", firstName);
        if (!isFirstNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid First Name!").show();
            return false;
        }

        String lastName = txtLastName.getText();

        boolean isLastNameValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", lastName);
        if (!isLastNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid First Name!").show();
            return false;
        }

        String position = txtPosition.getText();

        boolean isPositionValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", position);
        if (!isPositionValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Position!").show();
            return false;
        }

        String userName = txtUsername.getText();

        boolean isUserNameValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", userName);
        if (!isUserNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid UserName!").show();
            return false;
        }

        String password = txtPassword.getText();

        boolean isPasswordValidated = Pattern.matches("[a-zA-Z\\s\\d]+", password);
        if (!isPasswordValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Password!").show();
            return false;
        }

        return true;
    }
    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
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
