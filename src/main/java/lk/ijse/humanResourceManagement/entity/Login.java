package lk.ijse.humanResourceManagement.entity;

public class Login {
    private String userId;
    private String position;
    private String userName;
    private String password;

    public Login() {
    }

    public Login(String userId, String position, String userName, String password) {
        this.userId = userId;
        this.position = position;
        this.userName = userName;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
