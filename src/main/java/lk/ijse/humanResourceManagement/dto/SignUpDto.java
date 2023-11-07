package lk.ijse.humanResourceManagement.dto;

import javafx.scene.control.TextField;

public class SignUpDto {
    public String txtId;
    public String txtUsername;
    public String txtPassword;
    public String position;

    public SignUpDto() {
    }

    public SignUpDto(String txtId, String txtUsername, String txtPassword, String position) {
        this.txtId = txtId;
        this.txtUsername = txtUsername;
        this.txtPassword = txtPassword;
        this.position = position;
    }

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(String txtUsername) {
        this.txtUsername = txtUsername;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "SignUpDto{" +
                "txtId='" + txtId + '\'' +
                ", txtUsername='" + txtUsername + '\'' +
                ", txtPassword='" + txtPassword + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
