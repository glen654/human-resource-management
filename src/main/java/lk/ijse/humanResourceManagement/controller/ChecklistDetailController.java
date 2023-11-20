package lk.ijse.humanResourceManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.humanResourceManagement.dto.ChecklistDto;

public class ChecklistDetailController {
    @FXML
    private Label lblChecklistId;

    @FXML
    private Label lblDuedate;

    @FXML
    private Label lblOnboardingTask;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTask;

    @FXML
    private AnchorPane rootNode;

    public void searchChecklistDetail(ChecklistDto checklistDto) {
        lblTask.setText(checklistDto.getOnboardingTask());
        lblChecklistId.setText(checklistDto.getChecklist_id());
        lblOnboardingTask.setText(checklistDto.getOnboardingTask());
        lblDuedate.setText(String.valueOf(checklistDto.getDueDate()));
        lblStatus.setText(checklistDto.getStatus());
    }
}
