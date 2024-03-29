package lk.ijse.humanResourceManagement.entity;

import java.time.LocalDate;

public class Checklist {
    private String checklist_id;
    private String onboardingTask;
    private LocalDate dueDate;
    private String status;

    public Checklist() {
    }

    public Checklist(String checklist_id, String onboardingTask, LocalDate dueDate, String status) {
        this.checklist_id = checklist_id;
        this.onboardingTask = onboardingTask;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Checklist(String checklistId, String status) {
        this.checklist_id = checklistId;
        this.status = status;
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
}
