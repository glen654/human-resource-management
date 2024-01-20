package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.DepartmentBO;
import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.bo.custom.ProgramBO;
import lk.ijse.humanResourceManagement.bo.custom.RequestBO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;


import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public AnchorPane rootNode;
    public Label txtUserName;
    public Label lblDepartmentCount;
    public Label lblEmployeeCount;
    public PieChart employeePieChart;

    public BarChart departmentBarChart;

    @FXML
    private Label lblProgramCount;

    @FXML
    private Label lblRequestCount;

    public BarChart requestBarChart;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.EMPLOYEE);
    DepartmentBO departmentBO = (DepartmentBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.DEPARTMENT);
    ProgramBO programBO = (ProgramBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.PROGRAM);
    RequestBO requestBO = (RequestBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.REQUEST);

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Human Resource Management System");
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }
    @FXML
    void btnDepartmentOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/department.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setEmployeePieChart();
        setDepartmentBarChart();
        setProgramBarChart();

        int employeeCount = 0;
        try {
            employeeCount = employeeBO.getEmployeeCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int departmentCount = 0;
        try {
            departmentCount = departmentBO.getDepartmentCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int programCount = 0;
        try {
            programCount = programBO.getProgramCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int requestCount = 0;
        try {
            requestCount = requestBO.getRequestCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lblEmployeeCount.setText(String.valueOf(employeeCount));
        lblDepartmentCount.setText(String.valueOf(departmentCount));
        lblProgramCount.setText(String.valueOf(programCount));
        lblRequestCount.setText(String.valueOf(requestCount));
    }

    public void setEmployeePieChart(){
        try {
            int employeeCount = employeeBO.getEmployeeCount();
            int departmentCount = departmentBO.getDepartmentCount();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Employees", employeeCount),
                    new PieChart.Data("Departments", departmentCount)
            );
            employeePieChart.setData(pieChartData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void setDepartmentBarChart() {
        int departmentEmployeeCount;
        int employeeCount;
        try {
            departmentEmployeeCount = departmentBO.getDepartmentCount();
            employeeCount = employeeBO.getEmployeeCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        departmentBarChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Employee Data");

        String departmentId = "Department ID";
        String employeeId = "Employee ID";

        series.getData().add(new XYChart.Data<>(departmentId, departmentEmployeeCount));
        series.getData().add(new XYChart.Data<>(employeeId, employeeCount));

        departmentBarChart.getData().add(series);
    }

    public void setProgramBarChart() {
        int programCount;
        int employeeCount;
        try {
            programCount = programBO.getProgramCount();
            employeeCount = employeeBO.getEmployeeCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        requestBarChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Employee Data");

        String programId = "Program ID";
        String employeeId = "Employee ID";

        series.getData().add(new XYChart.Data<>(programId, programCount));
        series.getData().add(new XYChart.Data<>(employeeId, employeeCount));

        requestBarChart.getData().add(series);
    }
    @FXML
    void btnPerformanceOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/performanceReview_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }


    @FXML
    void btnLeaveRequestOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/leaveRequest_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnTrainingProgramOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/trainingProgram_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }
    @FXML
    void btnChecklistOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/onboardingChecklist_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/salary_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

    @FXML
    void btnAttendanceOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/attendance_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

}
