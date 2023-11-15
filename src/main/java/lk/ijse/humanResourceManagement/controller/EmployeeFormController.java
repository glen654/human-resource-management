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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.tm.EmployeeTm;
import lk.ijse.humanResourceManagement.model.EmployeeModel;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class EmployeeFormController {

    public Label txtUserName;
    public TextField txtSearchId;

    @FXML
    private TableColumn<EmployeeTm, JFXButton> colDeleteAction;

    @FXML
    private TableColumn<EmployeeDto, String> colDepartment;

    @FXML
    private TableColumn<EmployeeDto, String> colFirstName;

    @FXML
    private TableColumn<EmployeeDto, String> colId;

    @FXML
    private TableColumn<EmployeeDto, String> colJobRole;

    @FXML
    private TableColumn<EmployeeTm, JFXButton> colUpdateAction;


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

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
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

    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
        tableListener();

        colDeleteAction.setCellFactory(column -> {
            TableCell<EmployeeTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        EmployeeTm employeeTm = getTableView().getItems().get(getIndex());
                        handleDeleteAction(employeeTm.getId());
                    });
                }


                @Override
                protected void updateItem(JFXButton item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        setButtonStylesDelete(deleteButton);
                    }
                }
            };

            return cell;
        });

        colUpdateAction.setCellFactory(column -> {
            TableCell<EmployeeTm, JFXButton> cell = new TableCell<>() {
                final JFXButton updateButton = new JFXButton("Update");

                {
                    updateButton.setOnAction(event -> {
                        EmployeeTm employee = getTableView().getItems().get(getIndex());
                        openUpdateForm(employee);
                    });
                }

                @Override
                protected void updateItem(JFXButton item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                        setButtonStylesUpdate(updateButton);
                    }
                }
            };

            return cell;
        });
    }

    private void setButtonStylesUpdate(JFXButton updateButton) {
        updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setCursor(Cursor.HAND);
        colUpdateAction.setStyle("-fx-alignment: CENTER;");
        updateButton.setMaxWidth(100.0);
    }

    private void openUpdateForm(EmployeeTm employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employeeUpdate_form.fxml"));
            Parent rootNode = loader.load();

            EmployeeUpdateFormController updateFormController = loader.getController();
            updateFormController.setEmployeeData(employee);

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setTitle("Human Resource Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void setButtonStylesDelete(JFXButton deleteButton) {
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setCursor(Cursor.HAND);
        colDeleteAction.setStyle("-fx-alignment: CENTER;");
        deleteButton.setMaxWidth(100.0);
    }

    private void handleDeleteAction(String id) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = empModel.deleteEmployee(id);
                if (deleted) {
                    loadAllEmployee();
                    new Alert(Alert.AlertType.CONFIRMATION, "Department Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void loadAllEmployee() {
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
