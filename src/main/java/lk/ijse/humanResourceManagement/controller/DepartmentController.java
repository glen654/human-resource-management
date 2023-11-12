package lk.ijse.humanResourceManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.tm.DepartmentTm;
import lk.ijse.humanResourceManagement.model.DepartmentModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DepartmentController {
    public TextField txtSearchId;
    @FXML
    private TableColumn<DepartmentTm,JFXButton> colDeleteAction;

    @FXML
    private TableColumn<DepartmentTm,JFXButton> colUpdateAction;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<DepartmentTm> tblDepartment;

    @FXML
    private Label txtUserName;

    private DepartmentModel depModel = new DepartmentModel();


    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/department_add.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = new Stage();
        stage.setTitle("Human Resource Management System");

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws IOException {
        String id = txtSearchId.getText();

        try {
            DepartmentDto departmentDto = depModel.searchCustomer(id);

            if (departmentDto != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/department_details.fxml"));
                Parent rootNode = loader.load();

                // Get the controller instance
                DepartmentDetailsController detailsController = loader.getController();

                // Call the non-static method on the controller instance
                detailsController.searchDepartmentDetails(departmentDto);

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("Human Resource Management System");
                stage.setScene(scene);
                stage.show();

                clearFields();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Department not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }




    public void initialize(){
        setCellValueFactory();
        loadAllDepartments();
        tableListener();
    }

    private void tableListener() {
        tblDepartment.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(Object newValue) {
    }


    private void loadAllDepartments() {
        ObservableList<DepartmentTm> obList = FXCollections.observableArrayList();
        try {
            List<DepartmentDto> dtoList = depModel.loadAllDepartments();

            for (DepartmentDto dto : dtoList) {
                obList.add(new DepartmentTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getDesc(),
                        new JFXButton("Update"),
                        new JFXButton("Delete")

                ));
            }
            tblDepartment.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colUpdateAction.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnUpdate"));
        colDeleteAction.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    private void clearFields() {
        txtSearchId.setText("");
    }

}
