package lk.ijse.humanResourceManagement.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.tm.DepartmentTm;
import lk.ijse.humanResourceManagement.dto.tm.EmployeeTm;
import lk.ijse.humanResourceManagement.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    public Label txtUserName;
    public TextField txtSearchId;

    @FXML
    private TableColumn<?, ?> colDeleteAction;

    @FXML
    private TableColumn<EmployeeDto, String> colDepartment;

    @FXML
    private TableColumn<EmployeeDto, String> colFirstName;

    @FXML
    private TableColumn<EmployeeDto, String> colId;

    @FXML
    private TableColumn<EmployeeDto, String> colJobRole;

    @FXML
    private TableColumn<?, ?> colUpdateAction;



    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    private EmployeeModel empModel = new EmployeeModel();

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_add.fxml"));

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
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSearchId.getText();

        try {
            EmployeeDto employeeDto = empModel.searchEmployee(id);

            if (employeeDto != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employeeDetails_form.fxml"));
                Parent rootNode = loader.load();

                EmployeeDetailsFormController detailsController = loader.getController();

                detailsController.searchEmployeeDetails(employeeDto);

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("Human Resource Management System");
                stage.setScene(scene);
                stage.show();

                clearFields();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize(){
        setCellValueFactory();
        loadAllDepartments();
        tableListener();
    }

    private void tableListener() {
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(EmployeeTm row) {
        colId.setText(row.getId());
        colFirstName.setText(row.getFirstName());
        colJobRole.setText(row.getJobRole());
        colDepartment.setText(row.getDepId());
    }

    private void loadAllDepartments() {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> dtoList = empModel.loadAllEmployee();

            for (EmployeeDto dto : dtoList) {
                JFXButton updateButton = new JFXButton("Update");
                JFXButton deleteButton = new JFXButton("Delete");

                updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                updateButton.setCursor(Cursor.HAND);
                deleteButton.setCursor(Cursor.HAND);
                colUpdateAction.setStyle("-fx-alignment: CENTER;");
                colDeleteAction.setStyle("-fx-alignment: CENTER;");
                updateButton.setMaxWidth(100.0);
                deleteButton.setMaxWidth(100.0);

                obList.add(new EmployeeTm(
                        dto.getId(),
                        dto.getFirstName(),
                        dto.getJobRole(),
                        dto.getDepId(),
                        updateButton,
                        deleteButton

                ));
            }
            tblEmployee.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("depId"));
        colUpdateAction.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnUpdate"));
        colDeleteAction.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));
    }

    private void clearFields() {
        txtSearchId.setText("");
    }
}
