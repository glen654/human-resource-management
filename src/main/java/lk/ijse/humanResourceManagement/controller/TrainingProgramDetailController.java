package lk.ijse.humanResourceManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.humanResourceManagement.dto.ProgramDto;

public class TrainingProgramDetailController {
    @FXML
    private Label lblDescription;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblPId;

    @FXML
    private Label lblProgramId;

    @FXML
    private Label lblProgramName;

    @FXML
    private Label lblTrainer;

    public void searchProgramDetail(ProgramDto programDto) {
        lblPId.setText(programDto.getProgram_id());
        lblProgramId.setText(programDto.getProgram_id());
        lblProgramName.setText(programDto.getName());
        lblDescription.setText(programDto.getDescription());
        lblTrainer.setText(programDto.getTrainers());
        lblDuration.setText(programDto.getDuration());
    }
}
