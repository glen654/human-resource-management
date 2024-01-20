package lk.ijse.humanResourceManagement.controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.bo.BOFactory;
import lk.ijse.humanResourceManagement.bo.custom.EmployeeBO;
import lk.ijse.humanResourceManagement.bo.custom.PlaceEnrollmentBO;
import lk.ijse.humanResourceManagement.bo.custom.ProgramBO;
import lk.ijse.humanResourceManagement.dto.EmployeeDto;
import lk.ijse.humanResourceManagement.dto.ProgramDto;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class TrainingProgramAddController implements Initializable {
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

    ProgramBO programBO = (ProgramBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.PROGRAM);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.EMPLOYEE);
    PlaceEnrollmentBO placeEnrollmentBO = (PlaceEnrollmentBO) BOFactory.getBOFactory().getBo(BOFactory.BOTypes.PLACE_ENROLLMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
            String program_Id = programBO.generateNextProgramId();
            txtProgramId.setText(program_Id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
     void btnSaveOnAction(ActionEvent event) throws SQLException {
        if(validateProgram()){
            String program_id = txtProgramId.getText();
            String name = txtProgramName.getText();
            String desc = txtDescription.getText();
            String trainers = cmbTrainers.getValue();
            String duration = txtDuration.getText();
            String emp_id = cmbEmpId.getValue();

            var dto = new ProgramDto(program_id,name,desc,trainers,duration,emp_id);

            boolean isSaved = placeEnrollmentBO.placeEnrollment(dto);

            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Program And Enrollment Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Program not Enrollment Not Saved").show();
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

        private void loadAllEmployeeIds () {
            ObservableList<String> obList = FXCollections.observableArrayList();

            try {
                List<EmployeeDto> idList = employeeBO.loadAllEmployee();

                for (EmployeeDto dto : idList) {
                    obList.add(dto.getId());
                }

                cmbEmpId.setItems(obList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void clearFields () {
            txtProgramId.setText("");
            txtProgramName.setText("");
            txtDuration.setText("");
            txtDescription.setText("");
            cmbTrainers.setValue(null);
            cmbEmpId.setValue(null);
        }
    }
