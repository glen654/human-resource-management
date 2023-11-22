package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.ProgramDto;
import lk.ijse.humanResourceManagement.model.EmployeeModel;
import lk.ijse.humanResourceManagement.model.ProgramModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TrainingProgramAddController {
    @FXML
    private ComboBox<String> cmbTrainers;

    @FXML
    private ComboBox<String> cmbEmpId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtProgramName;

    private ProgramModel programModel = new ProgramModel();

    private EmployeeModel employeeModel= new EmployeeModel();
    public void initialize(){
        loadTrainers();
        generateNextProgramId();
        loadAllEmployeeIds();
    }

    private void loadTrainers() {
        List<String> trainers = Arrays.asList("Mr.Samarawickrama", "Mr.Rajapaksha", "Mr.Ranaweera","Mrs.Wijethunga");
        ObservableList<String> trainerList = FXCollections.observableArrayList(trainers);
        cmbTrainers.setItems(trainerList);
    }
    private void generateNextProgramId() {
        try {
            String program_Id = programModel.generateNextProgramId();
            txtProgramId.setText(program_Id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(validateProgram()){
            String program_id = txtProgramId.getText();
            String name = txtProgramName.getText();
            String description = txtDescription.getText();
            String trainers = cmbTrainers.getValue();
            String duration = txtDuration.getText();
            String emp_id = cmbEmpId.getValue();

            var dto = new ProgramDto(program_id,name,description,trainers,duration,emp_id);

            try {
                boolean isSaved = programModel.saveProgram(dto);

                if(isSaved){
                    clearFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Program Save Successfully").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Program can not Save").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean validateProgram(){
        String program_id = txtProgramId.getText();

        boolean isProgramIDValidated = Pattern.matches("[P][0-9]{3,}", program_id);
        if (!isProgramIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Program ID!").show();
            return false;
        }

        String name = txtProgramName.getText();

        boolean isNameValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", name);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Program Name!").show();
            return false;
        }

        String description = txtDescription.getText();

        boolean isDescriptionValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", description);
        if (!isDescriptionValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description!").show();
            return false;
        }


        String trainers = cmbTrainers.getValue();

        boolean isTrainerValidated = Pattern.matches("Mr.Samarawickrama|Mr.Rajapaksha|Mr.Ranaweera|Mrs.Wijethunga", trainers);
        if (!isTrainerValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Trainer!").show();
            return false;
        }

        String duration = txtDuration.getText();

        boolean isDurationValidated = Pattern.matches("[A-Z][a-zA-Z\\s\\d]+", duration);
        if (!isDurationValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Duration!").show();
            return false;
        }

        String emp_id = cmbEmpId.getValue();

        boolean isEmpIDValidated = Pattern.matches("[E][0-9]{3,}", emp_id);
        if (!isEmpIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }

        return true;
    }

    private void loadAllEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = employeeModel.loadAllEmployee();

            for (EmployeeDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearFields(){
        txtProgramId.setText("");
        txtProgramName.setText("");
        txtDuration.setText("");
        txtDescription.setText("");
        cmbTrainers.setValue(null);
        cmbEmpId.setValue(null);
    }
}
