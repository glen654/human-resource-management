package lk.ijse.humanResourceManagement.dto.tm;

import com.jfoenix.controls.JFXButton;

public class TrainingEnrollmentTm {
    private String enrollment_id;
    private String emp_id;
    private String program_id;
    private String name;
    private String status;

    private JFXButton btnUpdate;
    private JFXButton btnDelete;

    public TrainingEnrollmentTm() {
    }

    public TrainingEnrollmentTm(String enrollment_id, String emp_id, String program_id, String name, String status, JFXButton btnUpdate, JFXButton btnDelete) {
        this.enrollment_id = enrollment_id;
        this.emp_id = emp_id;
        this.program_id = program_id;
        this.name = name;
        this.status = status;
        this.btnUpdate = btnUpdate;
        this.btnDelete = btnDelete;
    }

    public String getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(String enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getProgram_id() {
        return program_id;
    }

    public void setProgram_id(String program_id) {
        this.program_id = program_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JFXButton getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(JFXButton btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public JFXButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JFXButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "TrainingEnrollmentTm{" +
                "enrollment_id='" + enrollment_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", program_id='" + program_id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", btnUpdate=" + btnUpdate +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
