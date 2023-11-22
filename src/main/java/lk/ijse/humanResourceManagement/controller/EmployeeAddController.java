package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.dto.DepartmentDto;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.model.DepartmentModel;
import lk.ijse.humanResourceManagement.model.EmployeeModel;

import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.regex.Pattern;

public class EmployeeAddController {
    @FXML
    private ComboBox<String> cmbDepartmentIds;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker txtDateOfBirth;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtJobRole;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextArea txtQualification;

    @FXML
    private TextField txtSalary;

    @FXML
    private ImageView imageView;
    private EmployeeModel empModel = new EmployeeModel();
   
    private DepartmentModel depModel = new DepartmentModel();
    
    public void initialize(){
        loadAllDepartmentIds();
        loadGenders();
        generateNextEmployeeId();
    }

    private void generateNextEmployeeId() {
        try {
            String empId = empModel.generateNextEmployeeId();
            txtId.setText(empId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadGenders() {
        List<String> genders = Arrays.asList("Male", "Female");
        ObservableList<String> genderList = FXCollections.observableArrayList(genders);
        cmbGender.setItems(genderList);
    }

    private void loadAllDepartmentIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<DepartmentDto> idList = depModel.loadAllDepartments();

            for (DepartmentDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbDepartmentIds.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML 
    void btnSaveOnAction(ActionEvent event) {
        if(validateEmployee()){
            String id = txtId.getText();
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String qualification = txtQualification.getText();
            String history = txtHistory.getText();
            String depId = cmbDepartmentIds.getValue();
            LocalDate dob =txtDateOfBirth.getValue();
            String dobString = dob != null ? dob.toString() : null;
            String gender = cmbGender.getValue();
            String email = txtEmail.getText();
            double salary = Double.parseDouble(txtSalary.getText());
            String role = txtJobRole.getText();

            var dto = new EmployeeDto(id, firstName, lastName, contact,qualification,history,depId,dob,gender,email,salary,role);

            try {
                boolean isSaved = empModel.saveEmployee(dto);

                if (isSaved) {
                    clearFields();
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Successfully!").show();

                    String employeeData = "ID: " + dto.getId() + "Name: " + dto.getFirstName() + " " + dto.getLastName();
                    WritableImage qrCodeImage = createQRCode(employeeData, 300, 300);

                    EmailSender mail = new EmailSender();
                    mail.setMsg("Congradulations! " + txtFirstName.getText() + txtLastName.getText() + "you are successfully added to the HR Navigator System of our company.");
                    mail.setTo(dto.getEmail());
                    mail.setSubject("Subject");
                    mail.setImage(qrCodeImage);

                    Thread thread = new Thread(mail);
                    thread.start();
                }else{
                    System.out.println("qr code generate failed");
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    }
    private WritableImage createQRCode(String data, int width, int height) {
        WritableImage qrCodeImage = QRCodeGeneratorClass.generateQRCode(data, width, height);

        imageView.setImage(qrCodeImage);

        return qrCodeImage;
    }

    public boolean validateEmployee(){
        String id = txtId.getText();

        boolean isEmployeeIDValidated = Pattern.matches("[E][0-9]{3,}", id);
        if (!isEmployeeIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }

        String firstName = txtFirstName.getText();

        boolean isfirstNameValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", firstName);
        if (!isfirstNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid First Name!").show();
            return false;
        }

        String lastName = txtLastName.getText();

        boolean islastNameValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", lastName);
        if (!islastNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid First Name!").show();
            return false;
        }

        try {
            int contact = Integer.parseInt((txtContact.getText()));
            if (contact < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact!").show();
            return false;
        }

        String qualification = txtQualification.getText();

        boolean isQualificationValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", qualification);
        if (!isQualificationValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Qualification!").show();
            return false;
        }

        String history = txtHistory.getText();

        boolean isHistoryValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", history);
        if (!isHistoryValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid History!").show();
            return false;
        }

        String depId = cmbDepartmentIds.getValue();

        boolean isDepIDValidated = Pattern.matches("[D][0-9]{3,}", depId);
        if (!isDepIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Department ID!").show();
            return false;
        }

        LocalDate startDate = txtDateOfBirth.getValue();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedStartDate = startDate.format(formatter);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Birth Date!").show();
            return false;
        }

        String gender = cmbGender.getValue();

        boolean isGenderValidated = Pattern.matches("Male|Female", gender);
        if (!isGenderValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Gender!").show();
            return false;
        }

        try {
            double salary = Double.parseDouble(txtSalary.getText());
            if (salary <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Salary!").show();
            return false;
        }

        String email = txtEmail.getText();

        boolean isEmailValidated = Pattern.matches("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\\\.])([a-zA-Z]+)$", email);
        if (!isEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!").show();
            return false;
        }

        String jobRole = txtJobRole.getText();

        boolean isJobRoleValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", jobRole);
        if (!isJobRoleValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Job Role!").show();
            return false;
        }

        return true;
    }

    private void clearFields() {
        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtContact.setText("");
        txtDateOfBirth.setValue(null);
        txtJobRole.setText("");
        txtEmail.setText("");
        txtHistory.setText("");
        txtQualification.setText("");
        txtSalary.setText("");
        cmbDepartmentIds.setValue(null);
        cmbGender.setValue(null);
    }
}
