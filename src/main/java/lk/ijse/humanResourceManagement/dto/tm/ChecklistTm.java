package lk.ijse.humanResourceManagement.dto.tm;

import com.jfoenix.controls.JFXButton;

import java.time.LocalDate;

public class ChecklistTm {
    private String checklist_id;
    private String onboardingTask;
    private LocalDate dueDate;
    private String status;
    private JFXButton btnIncomplete;
    private JFXButton btnDelete;

    public ChecklistTm() {
    }

    public ChecklistTm(String checklist_id, String onboardingTask, LocalDate dueDate, String status, JFXButton btnIncomplete, JFXButton btnDelete) {
        this.checklist_id = checklist_id;
        this.onboardingTask = onboardingTask;
        this.dueDate = dueDate;
        this.status = status;
        this.btnIncomplete = btnIncomplete;
        this.btnDelete = btnDelete;
    }

    public String getChecklist_id() {
        return checklist_id;
    }

    public void setChecklist_id(String checklist_id) {
        this.checklist_id = checklist_id;
    }

    public String getOnboardingTask() {
        return onboardingTask;
    }

    public void setOnboardingTask(String onboardingTask) {
        this.onboardingTask = onboardingTask;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JFXButton getBtnIncomplete() {
        return btnIncomplete;
    }

    public void setBtnIncomplete(JFXButton btnIncomplete) {
        this.btnIncomplete = btnIncomplete;
    }

    public JFXButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JFXButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "ChecklistTm{" +
                "checklist_id='" + checklist_id + '\'' +
                ", onboardingTask='" + onboardingTask + '\'' +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                ", btnIncomplete=" + btnIncomplete +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
