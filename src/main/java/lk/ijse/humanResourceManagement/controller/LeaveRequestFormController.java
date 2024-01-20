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
import lk.ijse.humanResourceManagement.bo.custom.RequestBO;
import lk.ijse.humanResourceManagement.dto.LeaveRequestDto;
import lk.ijse.humanResourceManagement.dto.tm.LeaveRequestTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LeaveRequestFormController implements Initializable {

    public AnchorPane rootNode;
    @FXML
    private TableColumn<LeaveRequestTm,JFXButton> colDelete;

    @FXML
    private TableColumn<LeaveRequestDto, String> colEmpId;

    @FXML
    private TableColumn<LeaveRequestDto, LocalDate> colEndDate;

    @FXML
    private TableColumn<LeaveRequestDto, String> colLeaveType;

    @FXML
    private TableColumn<LeaveRequestDto, LocalDate> colRequestDate;

    @FXML
    private TableColumn<LeaveRequestDto, String> colRequestId;

    @FXML
    private TableColumn<LeaveRequestDto, LocalDate> colStartDate;

    @FXML
    private TableColumn<LeaveRequestDto, String> colStatus;

    @FXML
    private TableColumn<LeaveRequestTm, JFXButton> colUpdate;

    @FXML
    private TextField txtSearchId;

    @FXML
    private Label txtUserName;

    @FXML
    private TableView<LeaveRequestTm> tblLeaveRequest;

    RequestBO requestBO = (RequestBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.REQUEST);

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/leaveRequestAdd.fxml"));

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
            LeaveRequestDto requestDto = requestBO.searchRequest(id);

            if (requestDto != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/leaveRequestDetails.fxml"));
                Parent rootNode = loader.load();

                LeaveRequestDetailsController detailsController = loader.getController();

                detailsController.searchRequestDetail(requestDto);

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("Human Resource Management System");
                stage.setScene(scene);
                stage.show();

                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Leave Request not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtSearchId.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setCellValueFactory();
        loadAllRequest();
        tableListener();

        colUpdate.setCellFactory(column -> {
            TableCell<LeaveRequestTm, JFXButton> cell = new TableCell<>() {
                final JFXButton updateButton = new JFXButton("Update");

                {
                    updateButton.setOnAction(event -> {
                        LeaveRequestTm request = getTableView().getItems().get(getIndex());
                        openUpdateForm(request);
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
            TableCell<LeaveRequestTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        LeaveRequestTm request = getTableView().getItems().get(getIndex());
                        handleDeleteAction(request.getRequest_id());
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

    private void handleDeleteAction(String requestId) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = requestBO.deleteRequest(requestId);
                if (deleted) {
                    loadAllRequest();
                    new Alert(Alert.AlertType.CONFIRMATION, "Leave Request Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void openUpdateForm(LeaveRequestTm request) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/leaveRequestUpdate.fxml"));
            Parent rootNode = loader.load();

            LeaveRequestUpdate updateFormController = loader.getController();
            updateFormController.setRequestData(request);

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
        tblLeaveRequest.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(LeaveRequestTm newValue) {
    }

    private void loadAllRequest() {
        ObservableList<LeaveRequestTm> obList = FXCollections.observableArrayList();
        try {
            List<LeaveRequestDto> dtoList = requestBO.loadAllRequest();

            for (LeaveRequestDto dto : dtoList) {
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

                obList.add(new LeaveRequestTm(
                        dto.getRequest_id(),
                        dto.getEmp_id(),
                        dto.getLeaveType(),
                        dto.getStartDate(),
                        dto.getEndDate(),
                        dto.getStatus(),
                        dto.getRequestDate(),
                        updateButton,
                        deleteButton

                ));
            }
            tblLeaveRequest.setItems(obList);
            tblLeaveRequest.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colRequestId.setCellValueFactory(new PropertyValueFactory<>("request_id"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colLeaveType.setCellValueFactory(new PropertyValueFactory<>("leaveType"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        colUpdate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnUpdate"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));
    }
}
