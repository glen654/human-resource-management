package lk.ijse.humanResourceManagement.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.LoginBO;
import lk.ijse.humanResourceManagement.dto.LoginDto;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUsername;
  
    public AnchorPane rootNode;
    public PasswordField txtPassword;
    LoginBO loginBO = (LoginBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.LOGIN);

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();


        try {
            LoginDto loginDto = loginBO.searchUser(userName,password);

            if(loginDto != null){
                clearFields();
                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

                Scene scene = new Scene(rootNode);

                Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Human Resource Management System");
            }else{
                clearFields();
                new Alert(Alert.AlertType.ERROR,"Username or Password incorrect").show();
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
        primaryStage.setTitle("Human Resource Management System");
    }
}
