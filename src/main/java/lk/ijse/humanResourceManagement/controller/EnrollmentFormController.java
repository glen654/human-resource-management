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
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.dto.TrainingEnrollmentDto;
import lk.ijse.humanResourceManagement.dto.tm.ChecklistTm;
import lk.ijse.humanResourceManagement.dto.tm.ProgramTm;
import lk.ijse.humanResourceManagement.dto.tm.TrainingEnrollmentTm;
import lk.ijse.humanResourceManagement.model.TrainingEnrollmentModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EnrollmentFormController {
    @FXML
    private TableColumn<TrainingEnrollmentTm, JFXButton> colDelete;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEnrollmentId;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<TrainingEnrollmentTm, JFXButton> colUpdate;

    @FXML
    private TableView<TrainingEnrollmentTm> tblEnrollment;

    @FXML
    private Label txtUserName;

    @FXML
    private AnchorPane rootNode;

    private TrainingEnrollmentModel enrollmentModel = new TrainingEnrollmentModel();
    public void initialize() {
        setCellValueFactory();
        loadAllEnrollment();
        tableListener();

        colUpdate.setCellFactory(column -> {
            TableCell<TrainingEnrollmentTm, JFXButton> cell = new TableCell<>() {
                final JFXButton updateButton = new JFXButton("Complete");

                {
                    updateButton.setOnAction(event -> {
                        TrainingEnrollmentTm enrollmentList = getTableView().getItems().get(getIndex());
                        handleUpdateAction(enrollmentList);
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

        colDelete.setCellFactory(column -> {
            TableCell<TrainingEnrollmentTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        TrainingEnrollmentTm enrollment = getTableView().getItems().get(getIndex());
                        handleDeleteAction(enrollment.getEnrollment_id());
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
    }

    private void handleDeleteAction(String enrollmentId) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = enrollmentModel.deleteEnrollment(enrollmentId);
                if (deleted) {
                    loadAllEnrollment();
                    new Alert(Alert.AlertType.CONFIRMATION, "Enrollment Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setButtonStyles(JFXButton deleteButton) {
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setCursor(Cursor.HAND);
        colDelete.setStyle("-fx-alignment: CENTER;");
        deleteButton.setMaxWidth(100.0);
    }

    private void setButtonStyles(JFXButton updateButton, String buttonText) {
        updateButton.setStyle("-fx-background-color: " + (buttonText.equals("Complete") ? "#2ecc71" : "#e67e22") +
                "; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setCursor(Cursor.HAND);
        updateButton.setMaxWidth(100.0);
    }

    private void handleUpdateAction(TrainingEnrollmentTm enrollmentList) {
        try {
            boolean isUpdated = enrollmentModel.updateEnrollment(enrollmentList.getEnrollment_id(), "Complete");

            if (isUpdated) {
                enrollmentList.getBtnUpdate().setText("Complete");
                enrollmentList.setStatus("Complete");
                setButtonStyles(enrollmentList.getBtnUpdate(), "Complete");
                tblEnrollment.refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update status").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating status: " + e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblEnrollment.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(Object newValue) {
    }

    private void loadAllEnrollment() {
        ObservableList<TrainingEnrollmentTm> obList = FXCollections.observableArrayList();
        try {
            List<TrainingEnrollmentDto> dtoList = enrollmentModel.loadAllEnrollment();

            for (TrainingEnrollmentDto dto : dtoList) {
                JFXButton updateButton = new JFXButton("Complete");
                JFXButton deleteButton = new JFXButton("Delete");

                updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                updateButton.setCursor(Cursor.HAND);
                deleteButton.setCursor(Cursor.HAND);
                colUpdate.setStyle("-fx-alignment: CENTER;");
                colDelete.setStyle("-fx-alignment: CENTER;");
                updateButton.setMaxWidth(100.0);
                deleteButton.setMaxWidth(100.0);

                obList.add(new TrainingEnrollmentTm(
                        dto.getEnrollment_id(),
                        dto.getEmp_id(),
                        dto.getProgram_id(),
                        dto.getName(),
                        dto.getStatus(),
                        updateButton,
                        deleteButton

                ));
            }
            tblEnrollment.setItems(obList);
            tblEnrollment.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colEnrollmentId.setCellValueFactory(new PropertyValueFactory<>("enrollment_id"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("program_id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colUpdate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnUpdate"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/trainingProgram_form.fxml"));

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


}
