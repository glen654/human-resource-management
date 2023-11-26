package lk.ijse.humanResourceManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.tm.DepartmentTm;
import lk.ijse.humanResourceManagement.model.DepartmentModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentController {
    public TextField txtSearchId;
    @FXML
    private TableColumn<DepartmentTm,JFXButton> colDeleteAction;

    @FXML
    private TableColumn<DepartmentTm,JFXButton> colUpdateAction;

    @FXML
    private TableColumn<EmployeeDto, String> colDesc;

    @FXML
    private TableColumn<EmployeeDto, String> colId;

    @FXML
    private TableColumn<EmployeeDto, String> colName;

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
            DepartmentDto departmentDto = depModel.searchDepartment(id);

            if (departmentDto != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/department_details.fxml"));
                Parent rootNode = loader.load();

                DepartmentDetailsController detailsController = loader.getController();

                detailsController.searchDepartmentDetails(departmentDto);

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("Human Resource Management System");
                stage.setScene(scene);
                stage.show();

                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Department not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }




    public void initialize(){
        setCellValueFactory();
        loadAllDepartments();
        tableListener();

        colDeleteAction.setCellFactory(column -> {
            TableCell<DepartmentTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        DepartmentTm department = getTableView().getItems().get(getIndex());
                        handleDeleteAction(department.getId());
                    });
                }

                @Override
                protected void updateItem(JFXButton item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        setButtonStyles(deleteButton);
                    }
                }
            };

            return cell;
        });

        colUpdateAction.setCellFactory(column -> {
            TableCell<DepartmentTm, JFXButton> cell = new TableCell<>() {
                final JFXButton updateButton = new JFXButton("Update");

                {
                    updateButton.setOnAction(event -> {
                        DepartmentTm department = getTableView().getItems().get(getIndex());
                        openUpdateForm(department);
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

    private void openUpdateForm(DepartmentTm department) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/departmentUpdate_form.fxml"));
            Parent rootNode = loader.load();

            DepartmentUpdateFormController updateFormController = loader.getController();
            updateFormController.setDepartmentData(department);

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setTitle("Human Resource Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void setButtonStyles(JFXButton deleteButton) {
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setCursor(Cursor.HAND);
        colDeleteAction.setStyle("-fx-alignment: CENTER;");
        deleteButton.setMaxWidth(100.0);
    }

    private void setButtonStylesUpdate(JFXButton updateButton) {
        updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setCursor(Cursor.HAND);
        colUpdateAction.setStyle("-fx-alignment: CENTER;");
        updateButton.setMaxWidth(100.0);
    }
    private void handleDeleteAction(String id) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = depModel.deleteDepartment(id);
                if (deleted) {
                    loadAllDepartments();
                    new Alert(Alert.AlertType.CONFIRMATION, "Department Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void tableListener() {
        tblDepartment.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
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

                obList.add(new DepartmentTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getDesc(),
                        updateButton,
                        deleteButton

                ));
            }

            tblDepartment.setItems(obList);
            tblDepartment.refresh();
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
