package lk.ijse.humanResourceManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.SalaryDto;

public class SalaryDetailsFormController {
    @FXML
    private Label lblBasicSalary;

    @FXML
    private Label lblDeduction;

    @FXML
    private Label lblEmpContact;

    @FXML
    private Label lblEmpEmail;

    @FXML
    private Label lblEmpName;

    @FXML
    private Label lblEpf;

    @FXML
    private Label lblNetPay;

    @FXML
    private Label lblOverTime;

    @FXML
    private Label lblTotalEarnings;

    @FXML
    private Label lblTotalDeduction;



    public void searchSalaryDetails(SalaryDto salaryDto,EmployeeDto employeeDto) {
        double basicSalary = salaryDto.getSalary();
        double overTime = salaryDto.getOverTime();
        double epf = salaryDto.getEpf();
        double salaryDeduction = salaryDto.getSalaryDeduction();


        double totalEarnings = basicSalary + overTime;

        double netPay = totalEarnings - epf - salaryDeduction;

        lblBasicSalary.setText(String.valueOf(basicSalary));
        lblOverTime.setText(String.valueOf(overTime));
        lblTotalEarnings.setText(String.valueOf(totalEarnings));
        lblEpf.setText(String.valueOf(epf));
        lblDeduction.setText(String.valueOf(salaryDeduction));
        lblTotalDeduction.setText(String.valueOf(epf + salaryDeduction));
        lblNetPay.setText(String.valueOf(netPay));
        lblEmpName.setText(employeeDto.getFirstName() + employeeDto.getLastName());
        lblEmpContact.setText(String.valueOf(employeeDto.getContact()));
        lblEmpEmail.setText(employeeDto.getEmail());
    }
}
