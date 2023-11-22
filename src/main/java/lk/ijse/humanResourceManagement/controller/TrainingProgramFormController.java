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
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.dto.tm.ProgramTm;
import lk.ijse.humanResourceManagement.dto.tm.ReviewTm;
import lk.ijse.humanResourceManagement.model.ProgramModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TrainingProgramFormController {
    @FXML
    private TableColumn<ProgramTm, JFXButton> colDelete;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colTrainers;

    @FXML
    private TableColumn<ProgramTm, JFXButton> colUpdate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ProgramTm> tblProgram;

    @FXML
    private TextField txtProgramId;

    @FXML
    private Label txtUserName;

    private ProgramModel programModel = new ProgramModel();

    public void initialize(){
        setCellValueFactory();
        loadAllPrograms();
        tableListener();

        colUpdate.setCellFactory(column -> {
            TableCell<ProgramTm, JFXButton> cell = new TableCell<>() {
                final JFXButton updateButton = new JFXButton("Update");

                {
                    updateButton.setOnAction(event -> {
                        ProgramTm program = getTableView().getItems().get(getIndex());
                        openUpdateForm(program);
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

        colDelete.setCellFactory(column -> {
            TableCell<ProgramTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        ProgramTm program = getTableView().getItems().get(getIndex());
                        handleDeleteAction(program.getProgram_id());
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

    private void setButtonStyles(JFXButton deleteButton) {
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setCursor(Cursor.HAND);
        colDelete.setStyle("-fx-alignment: CENTER;");
        deleteButton.setMaxWidth(100.0);
    }

    private void handleDeleteAction(String programId) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = programModel.deleteProgram(programId);
                if (deleted) {
                    loadAllPrograms();
                    new Alert(Alert.AlertType.CONFIRMATION, "Program Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void openUpdateForm(ProgramTm program) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/trainingProgramUpdate.fxml"));
            Parent rootNode = loader.load();

            TrainingProgramUpdateController updateFormController = loader.getController();
            updateFormController.setProgramData(program);

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setTitle("Human Resource Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setButtonStylesUpdate(JFXButton updateButton) {
        updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setCursor(Cursor.HAND);
        colUpdate.setStyle("-fx-alignment: CENTER;");
        updateButton.setMaxWidth(100.0);
    }

    private void tableListener() {
        tblProgram.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(Object newValue) {
    }

    private void loadAllPrograms() {
        ObservableList<ProgramTm> obList = FXCollections.observableArrayList();
        try {
            List<ProgramDto> dtoList = programModel.loadAllPrograms();

            for (ProgramDto dto : dtoList) {
                JFXButton updateButton = new JFXButton("Update");
                JFXButton deleteButton = new JFXButton("Delete");

                updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                updateButton.setCursor(Cursor.HAND);
                deleteButton.setCursor(Cursor.HAND);
                colUpdate.setStyle("-fx-alignment: CENTER;");
                colDelete.setStyle("-fx-alignment: CENTER;");
                updateButton.setMaxWidth(100.0);
                deleteButton.setMaxWidth(100.0);

                obList.add(new ProgramTm(
                        dto.getProgram_id(),
                        dto.getName(),
                        dto.getDescription(),
                        dto.getTrainers(),
                        dto.getDuration(),
                        dto.getEmp_id(),
                        updateButton,
                        deleteButton

                ));
            }
            tblProgram.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("program_id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colTrainers.setCellValueFactory(new PropertyValueFactory<>("trainers"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colUpdate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnUpdate"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/trainingProgramAdd.fxml"));

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
        String id = txtProgramId.getText();

        try {
            ProgramDto programDto = programModel.searchProgram(id);

            if (programDto != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/trainingProgramDetail.fxml"));
                Parent rootNode = loader.load();

                TrainingProgramDetailController detailsController = loader.getController();

                detailsController.searchProgramDetail(programDto);

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("Human Resource Management System");
                stage.setScene(scene);
                stage.show();

                clearFields();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Program not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtProgramId.setText("");
    }
}
