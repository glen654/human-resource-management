package lk.ijse.humanResourceManagement.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.LoginDto;
import lk.ijse.humanResourceManagement.model.LoginModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUsername;
  
    public AnchorPane rootNode;
    public PasswordField txtPassword;


    public void btnSignInOnAction(ActionEvent actionEvent) {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();



        LoginModel loginModel = new LoginModel();

        try {
            LoginDto loginDto = loginModel.searchUser(userName,password);

            if(loginDto != null){
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION,"Login Successful").show();
            }else{
                clearFields();
                new Alert(Alert.AlertType.INFORMATION,"Username or Password incorrect").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void btnEnterOnAction(KeyEvent keyEvent) {
        
    }

    public void btnSignUpPageOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signUp_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SignUp Form");
    }
}
