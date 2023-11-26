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
import lk.ijse.humanResourceManagement.db.DbConnection;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.dto.SalaryDto;
import lk.ijse.humanResourceManagement.dto.tm.ProgramTm;
import lk.ijse.humanResourceManagement.dto.tm.SalaryTm;
import lk.ijse.humanResourceManagement.model.EmployeeModel;
import lk.ijse.humanResourceManagement.model.SalaryModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SalaryFormController {
    @FXML
    private TableColumn<SalaryTm, JFXButton> colDelete;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEpf;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colSalaryDeduction;

    @FXML
    private TableColumn<?, ?> colSalaryId;


    @FXML
    private TableColumn<?, ?> colOverTime;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private TextField txtSalaryId;

    @FXML
    private Label txtUserName;

    private SalaryModel salaryModel = new SalaryModel();
    private EmployeeModel employeeModel = new EmployeeModel();
    public void initialize(){
        setCellValueFactory();
        loadAllSalary();
        tableListener();

        colDelete.setCellFactory(column -> {
            TableCell<SalaryTm, JFXButton> cell = new TableCell<>() {
                final JFXButton deleteButton = new JFXButton("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        SalaryTm salary = getTableView().getItems().get(getIndex());
                        handleDeleteAction(salary.getCompensation_id());
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

    private void handleDeleteAction(String compensationId) {
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                boolean deleted = salaryModel.deleteSalary(compensationId);
                if (deleted) {
                    loadAllSalary();
                    new Alert(Alert.AlertType.CONFIRMATION, "Salary Deleted Successfully").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableListener() {
        tblSalary.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(Object newValue) {
    }

    private void loadAllSalary() {
        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();
        try {
            List<SalaryDto> dtoList = salaryModel.loadAllSalary();

            for (SalaryDto dto : dtoList) {
                JFXButton deleteButton = new JFXButton("Delete");

                deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setCursor(Cursor.HAND);
                colDelete.setStyle("-fx-alignment: CENTER;");
                deleteButton.setMaxWidth(100.0);

                obList.add(new SalaryTm(
                        dto.getCompensation_id(),
                        dto.getEmp_id(),
                        dto.getSalary(),
                        dto.getStatus(),
                        dto.getSalaryDeduction(),
                        dto.getEpf(),
                        dto.getOverTime(),
                        deleteButton

                ));
            }
            tblSalary.setItems(obList);
            tblSalary.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("compensation_id"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colSalaryDeduction.setCellValueFactory(new PropertyValueFactory<>("salaryDeduction"));
        colEpf.setCellValueFactory(new PropertyValueFactory<>("epf"));
        colOverTime.setCellValueFactory(new PropertyValueFactory<>("overTime"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/salaryAdd_form.fxml"));

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
        String id = txtSalaryId.getText();

        try {
            SalaryDto salaryDto = salaryModel.searchSalary(id);

                if (salaryDto != null) {

                    EmployeeDto employeeDto = salaryModel.searchEmployeeBySalaryId(id);

                    if (employeeDto != null) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/salaryDetails_form.fxml"));
                        Parent rootNode = loader.load();

                        SalaryDetailsFormController salarydetailsController = loader.getController();

                        salarydetailsController.searchSalaryDetails(salaryDto, employeeDto);

                        Scene scene = new Scene(rootNode);

                        Stage stage = new Stage();
                        stage.setTitle("Human Resource Management System");
                        stage.setScene(scene);
                        stage.show();

                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Employee not found").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Salary not found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }


    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        String compensation_id = txtSalaryId.getText();

        Connection connection = DbConnection.getInstance().getConnection();;

        InputStream resourceAsStream = getClass().getResourceAsStream("/report/SalarySlipNew.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        System.out.println("SQL Query: " + jasperReport.getQuery().getText());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("compensation_id", compensation_id);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,connection);


        JasperViewer.viewReport(jasperPrint, false);
    }

    private void clearFields(){
        txtSalaryId.setText("");
    }
}

