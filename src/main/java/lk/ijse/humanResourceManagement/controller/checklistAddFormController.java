package lk.ijse.humanResourceManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.humanResourceManagement.dto.ChecklistDto;
import lk.ijse.humanResourceManagement.model.ChecklistModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class checklistAddFormController {
    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TextField txtChecklistId;

    @FXML
    private DatePicker txtDueDate;

    @FXML
    private TextField txtTask;

    private ChecklistModel checklistModel = new ChecklistModel();

    public void initialize(){
        loadStatus();
        generateNextTaskId();
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(validateTask()){
            String checklist_id = txtChecklistId.getText();
            String onboardingTasks = txtTask.getText();
            LocalDate dueDate = txtDueDate.getValue();
            String status = cmbStatus.getValue();

            var dto = new ChecklistDto(checklist_id,onboardingTasks,dueDate,status);

            try {
                boolean isSaved = checklistModel.saveChecklist(dto);

                if(isSaved){
                    clearFields();
                    new Alert(Alert.AlertType.CONFIRMATION,"Task saved successfully").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Task save unsuccessfull").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        txtChecklistId.setText("");
        txtTask.setText("");
        cmbStatus.setValue(null);
        txtDueDate.setValue(null);
    }

    private void loadStatus() {
        List<String> status = Arrays.asList("Completed","Incompleted");
        ObservableList<String> statusList = FXCollections.observableArrayList(status);
        cmbStatus.setItems(statusList);
    }

    public boolean validateTask(){
        String checklist_id = txtChecklistId.getText();

        boolean isChecklistIDValidated = Pattern.matches("[C][0-9]{3,}", checklist_id);
        if (!isChecklistIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Checklist ID!").show();
            return false;
        }

        String onboardingTasks = txtTask.getText();

        boolean isonboardingTasksValidated = Pattern.matches("[A-Z][a-zA-Z\\s]+", onboardingTasks);
        if (!isonboardingTasksValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Onboarding Task!").show();
            return false;
        }

        LocalDate dueDate = txtDueDate.getValue();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedEndDate = dueDate.format(formatter);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Due Date!").show();
            return false;
        }


        String status = cmbStatus.getValue();

        boolean isStatusValidated = Pattern.matches("Completed|Incompleted", status);
        if (!isStatusValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Status!").show();
            return false;
        }


        return true;
    }

    private void generateNextTaskId() {
        try {
            String checklist_Id = checklistModel.generateNextChecklistId();
            txtChecklistId.setText(checklist_Id);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
