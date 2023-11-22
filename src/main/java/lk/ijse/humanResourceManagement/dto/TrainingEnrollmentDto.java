package lk.ijse.humanResourceManagement.dto;

public class TrainingEnrollmentDto {
    private String enrollment_id;
    private String emp_id;
    private String program_id;
    private String name;
    private String status;

    public TrainingEnrollmentDto() {
    }

    public TrainingEnrollmentDto(String enrollment_id, String emp_id, String program_id, String name, String status) {
        this.enrollment_id = enrollment_id;
        this.emp_id = emp_id;
        this.program_id = program_id;
        this.name = name;
        this.status = status;
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

    @Override
    public String toString() {
        return "TrainingEnrollmentDto{" +
                "enrollment_id='" + enrollment_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                ", program_id='" + program_id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
