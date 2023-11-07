package lk.ijse.humanResourceManagement.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.humanResourceManagement.dto.LoginDto;
import lk.ijse.humanResourceManagement.model.LoginModel;

import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUsername;
    public TextField txtPassword;

    public void btnSignInOnAction(ActionEvent actionEvent) {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();

        LoginModel loginModel = new LoginModel();

        try {
            LoginDto loginDto = loginModel.searchUser(userName,password);

            if(loginDto != null){
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
        String userName = txtUsername.getText();
        String password = txtPassword.getText();

        LoginModel loginModel = new LoginModel();

        try {
            LoginDto loginDto = loginModel.searchUser(userName,password);

            if(loginDto != null){
                new Alert(Alert.AlertType.CONFIRMATION,"Login Successful").show();
            }else{
                clearFields();
                new Alert(Alert.AlertType.INFORMATION,"Username or Password incorrect").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
