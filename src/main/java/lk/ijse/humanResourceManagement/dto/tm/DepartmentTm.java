package lk.ijse.humanResourceManagement.dto.tm;


import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;

public class DepartmentTm {
    private String id;
    private String name;
    private String desc;

    private JFXButton btnUpdate;
    private JFXButton btnDelete;

    {

        btnUpdate = new JFXButton("Update");
        btnDelete = new JFXButton("Delete");

        // Set button styles
        btnUpdate.setCursor(javafx.scene.Cursor.HAND);
        btnUpdate.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: #ffffff");

        btnDelete.setCursor(javafx.scene.Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnUpdate.setPrefWidth(100);
        btnUpdate.setPrefHeight(30);
        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);


    }


    public DepartmentTm() {
    }

    public DepartmentTm(String id, String name, String desc, JFXButton btnUpdate, JFXButton btnDelete) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.btnUpdate = btnUpdate;
        this.btnDelete = btnDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public JFXButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JFXButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JFXButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JFXButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "DepartmentTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", btnUpdate=" + btnUpdate +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
