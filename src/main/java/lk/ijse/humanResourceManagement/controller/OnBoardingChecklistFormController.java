package lk.ijse.humanResourceManagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.ChecklistBO;
import lk.ijse.humanResourceManagement.dto.ChecklistDto;
import lk.ijse.humanResourceManagement.dto.tm.ChecklistTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OnBoardingChecklistFormController implements Initializable {
    @FXML
    private TableColumn<ChecklistDto, String> colChecklistId;

    @FXML
    private TableColumn<ChecklistTm, JFXButton> colDelete;

    @FXML
    private TableColumn<ChecklistDto, LocalDate> colDueDate;

    @FXML
    private TableColumn<ChecklistDto, String> colStatus;

    @FXML
    private TableColumn<ChecklistDto, String> colTask;

    @FXML
    private TableColumn<ChecklistTm, JFXButton> colUpdate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ChecklistTm> tblOnBoarding;

    @FXML
    private Label txtUserName;

    @FXML
    private TextField txtobId;

    ChecklistBO checklistBO = (ChecklistBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.CHECKLIST);

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/checklistAdd_form.fxml"));

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
    void btnSearchOnAction(ActionEvent event) throws IOException {
        String id = txtobId.getText();

        try {
            ChecklistDto checklistDto = checklistBO.searchTask(id);

            if (checklistDto != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/checklistDetail.fxml"));
                Parent rootNode = loader.load();

                ChecklistDetailController detailsController = loader.getController();

                detailsController.searchChecklistDetail(checklistDto);

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("Human Resource Management System");
                stage.setScene(scene);
                stage.show();

                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Program not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtobId.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setCellValueFactory();
        loadAllTask();
        tableListener();

        colDelete.setCellFactory(column -> {
            TableCell<ChecklistTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        ChecklistTm checkList = getTableView().getItems().get(getIndex());
                        handleDeleteAction(checkList.getChecklist_id());
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


        colUpdate.setCellFactory(column -> {
            TableCell<ChecklistTm, JFXButton> cell = new TableCell<>() {
                final JFXButton updateButton = new JFXButton("Completed");

                {
                    updateButton.setOnAction(event -> {
                        ChecklistTm checkList = getTableView().getItems().get(getIndex());
                        handleUpdateAction(checkList);
                    });
                }

                @Override
                protected void updateItem(JFXButton item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                        setButtonStyles(updateButton, item.getText());
                    }
                }
            };

            return cell;
        });

    }

    private void handleUpdateAction(ChecklistTm checkList) {
        try {
            boolean isUpdated = checklistBO.updateTask(checkList.getChecklist_id(), "Completed");

            if (isUpdated) {
                checkList.getBtnIncomplete().setText("Completed");
                checkList.setStatus("Completed");
                setButtonStyles(checkList.getBtnIncomplete(), "Completed");
                tblOnBoarding.refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update status").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating status: " + e.getMessage()).show();
        }
    }

    private void setButtonStyles(JFXButton updateButton, String buttonText) {
        updateButton.setStyle("-fx-background-color: " + (buttonText.equals("Completed") ? "#2ecc71" : "#e67e22") +
                "; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setCursor(Cursor.HAND);
        updateButton.setMaxWidth(100.0);
    }


    private void setButtonStyles(JFXButton deleteButton) {
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setCursor(Cursor.HAND);
        colDelete.setStyle("-fx-alignment: CENTER;");
        deleteButton.setMaxWidth(100.0);
    }

    private void handleDeleteAction(String checklistId) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = checklistBO.deleteTask(checklistId);
                if (deleted) {
                    loadAllTask();
                    new Alert(Alert.AlertType.CONFIRMATION, "Task Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableListener() {
        tblOnBoarding.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(ChecklistTm newValue) {
    }

    private void loadAllTask() {
        ObservableList<ChecklistTm> obList = FXCollections.observableArrayList();
        try {
            List<ChecklistDto> dtoList = checklistBO.loadAllTasks();

            for (ChecklistDto dto : dtoList) {
                JFXButton updateButton = new JFXButton("Incomplete");
                JFXButton deleteButton = new JFXButton("Delete");

                updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                updateButton.setCursor(Cursor.HAND);
                deleteButton.setCursor(Cursor.HAND);
                colUpdate.setStyle("-fx-alignment: CENTER;");
                colDelete.setStyle("-fx-alignment: CENTER;");
                updateButton.setMaxWidth(100.0);
                deleteButton.setMaxWidth(100.0);

                obList.add(new ChecklistTm(
                        dto.getChecklist_id(),
                        dto.getOnboardingTask(),
                        dto.getDueDate(),
                        dto.getStatus(),
                        updateButton,
                        deleteButton

                ));
            }
            tblOnBoarding.setItems(obList);
            tblOnBoarding.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colChecklistId.setCellValueFactory(new PropertyValueFactory<>("checklist_id"));
        colTask.setCellValueFactory(new PropertyValueFactory<>("onboardingTask"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colUpdate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnIncomplete"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));
    }
}
