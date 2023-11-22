package lk.ijse.humanResourceManagement.dto;

public class ProgramDto {
    private String program_id;
    private String name;
    private String description;
    private String trainers;
    private String duration;
    private String emp_id;

    public ProgramDto() {
    }

    public ProgramDto(String program_id, String name, String description, String trainers, String duration, String emp_id) {
        this.program_id = program_id;
        this.name = name;
        this.description = description;
        this.trainers = trainers;
        this.duration = duration;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrainers() {
        return trainers;
    }

    public void setTrainers(String trainers) {
        this.trainers = trainers;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    @Override
    public String toString() {
        return "ProgramDto{" +
                "program_id='" + program_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trainers='" + trainers + '\'' +
                ", duration='" + duration + '\'' +
                ", emp_id='" + emp_id + '\'' +
                '}';
    }
}
