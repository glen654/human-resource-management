package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.humanResourceManagement.dto.tm.DepartmentTm;
import lk.ijse.humanResourceManagement.model.DepartmentModel;
import lk.ijse.humanResourceManagement.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardController {

    public AnchorPane rootNode;
    public Label txtUserName;
    public Label lblDepartmentCount;
    public Label lblEmployeeCount;
    public PieChart employeePieChart;

    public BarChart departmentBarChart;


    private EmployeeModel empModel = new EmployeeModel();

    private DepartmentModel depModel = new DepartmentModel();

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
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

    public void initialize(){
        setEmployeePieChart();
        setDepartmentBarChart();

        int employeeCount = 0;
        try {
            employeeCount = empModel.getEmployeeCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int departmentCount = 0;
        try {
            departmentCount = depModel.getDepartmentCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        lblEmployeeCount.setText(String.valueOf(employeeCount));
        lblDepartmentCount.setText(String.valueOf(departmentCount));
    }

    public void setEmployeePieChart(){
        try {
            int employeeCount = empModel.getEmployeeCount();
            int departmentCount = depModel.getDepartmentCount();

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
            departmentEmployeeCount = depModel.getDepartmentCount();
            employeeCount = empModel.getEmployeeCount();
        } catch (SQLException e) {
            // Handle the exception (show an alert, log the error, etc.)
            e.printStackTrace();
            return;
        }

        // Clear existing data in the chart
        departmentBarChart.getData().clear();

        // Populate the bar chart with data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Employee Data");

        // Assuming you have a specific department ID for this count
        String departmentId = "Department ID"; // Replace with the actual department ID
        String employeeId = "Employee ID";

        series.getData().add(new XYChart.Data<>(departmentId, departmentEmployeeCount));
        series.getData().add(new XYChart.Data<>(employeeId, employeeCount));
        // Set up the bar chart
        departmentBarChart.getData().add(series);
    }

    @FXML
    void btnPerformanceOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/performanceReview_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage =(Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Human Resource Management System");
    }

}
