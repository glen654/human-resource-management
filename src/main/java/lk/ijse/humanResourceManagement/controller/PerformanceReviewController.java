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
import lk.ijse.humanResourceManagement.bo.custom.ReviewBO;
import lk.ijse.humanResourceManagement.dto.ReviewDto;
import lk.ijse.humanResourceManagement.dto.tm.ReviewTm;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PerformanceReviewController implements Initializable {
    @FXML
    private TableColumn<ReviewDto, String> colComments;

    @FXML
    private TableColumn<ReviewDto, LocalDate> colDate;

    @FXML
    private TableColumn<ReviewTm, JFXButton> colDelete;

    @FXML
    private TableColumn<ReviewDto, String> colEmpId;

    @FXML
    private TableColumn<ReviewDto, String> colRatings;

    @FXML
    private TableColumn<ReviewDto, String> colReviewId;

    @FXML
    private TableColumn<ReviewTm, JFXButton> colUpdate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ReviewTm> tblReview;

    @FXML
    private TextField txtSearchId;

    @FXML
    private Label txtUserName;

    ReviewBO reviewBO = (ReviewBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.REVIEW);

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/reviewAdd_form.fxml"));

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
        String id = txtSearchId.getText();

        try {
            ReviewDto reviewDto = reviewBO.searchReview(id);

            if (reviewDto != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reviewDetail_form.fxml"));
                Parent rootNode = loader.load();

                ReviewDetailController detailsController = loader.getController();

                detailsController.searchReviewDetail(reviewDto);

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("Human Resource Management System");
                stage.setScene(scene);
                stage.show();

                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Review not found").show();
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
        loadAllReviews();
        tableListener();

        colUpdate.setCellFactory(column -> {
            TableCell<ReviewTm, JFXButton> cell = new TableCell<>() {
                final JFXButton updateButton = new JFXButton("Update");

                {
                    updateButton.setOnAction(event -> {
                        ReviewTm review = getTableView().getItems().get(getIndex());
                        openUpdateForm(review);
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
            TableCell<ReviewTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        ReviewTm review = getTableView().getItems().get(getIndex());
                        handleDeleteAction(review.getReviewId());
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

    private void handleDeleteAction(String id) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = reviewBO.deleteReview(id);
                if (deleted) {
                    loadAllReviews();
                    new Alert(Alert.AlertType.CONFIRMATION, "Review Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setButtonStylesUpdate(JFXButton updateButton) {
        updateButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        updateButton.setCursor(Cursor.HAND);
        colUpdate.setStyle("-fx-alignment: CENTER;");
        updateButton.setMaxWidth(100.0);
    }

    private void openUpdateForm(ReviewTm review) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reviewUpdate_form.fxml"));
            Parent rootNode = loader.load();

            ReviewUpdateFormController updateFormController = loader.getController();
            updateFormController.setReviewData(review);

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setTitle("Human Resource Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void tableListener() {
        tblReview.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(Object newValue) {
    }

    private void loadAllReviews() {
        ObservableList<ReviewTm> obList = FXCollections.observableArrayList();
        try {
            List<ReviewDto> dtoList = reviewBO.loadAllReviews();

            for (ReviewDto dto : dtoList) {
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

                obList.add(new ReviewTm(
                        dto.getReviewId(),
                        dto.getEmpId(),
                        dto.getComments(),
                        dto.getRating(),
                        dto.getDate(),
                        updateButton,
                        deleteButton

                ));
            }
            tblReview.setItems(obList);
            tblReview.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colReviewId.setCellValueFactory(new PropertyValueFactory<>("reviewId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colComments.setCellValueFactory(new PropertyValueFactory<>("comments"));
        colRatings.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUpdate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("deleteButton"));
    }
}

