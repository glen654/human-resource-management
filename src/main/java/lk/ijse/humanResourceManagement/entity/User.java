package lk.ijse.humanResourceManagement.entity;

public class User {
    private String firstName;
    private String lastName;
    private String position;
    private String userName;
    private String confirmPassword;

    public User() {
    }

    public User(String firstName, String lastName, String position, String userName, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.userName = userName;
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
