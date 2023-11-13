CREATE DATABASE humanResourceManagement;

USE humanResourceManagement;

CREATE TABLE user(
                     first_name VARCHAR(20) PRIMARY KEY ,
                     last_name VARCHAR(25) NOT NULL ,
                     position VARCHAR(25) NOT NULL,
                     userName VARCHAR(20) NOT NULL,
                     password VARCHAR(25) NOT NULL
);

CREATE TABLE department(
                           department_id VARCHAR(5) PRIMARY KEY,
                           departmentName VARCHAR(20) NOT NULL,
                           description VARCHAR(200) NOT NULL
);

CREATE TABLE employee(
                         emp_id VARCHAR(10) PRIMARY KEY,
                         firstName VARCHAR(20) NOT NULL,
                         lastName VARCHAR(20) NOT NULL,
                         emp_contact INT(10) NOT NULL,
                         emp_qualification VARCHAR(500) NOT NULL,
                         emp_history VARCHAR(500) NOT NULL,
                         department_id VARCHAR(5),
                         date_of_birth DATE NOT NULL ,
                         gender ENUM('Male','Female') NOT NULL,
                         email VARCHAR(50) NOT NULL ,
                         salary DOUBLE(12,2) NOT NULL,
                         FOREIGN KEY (department_id) REFERENCES department(department_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE attendance(
                           attendance_id VARCHAR(10) PRIMARY KEY,
                           date DATE NOT NULL,
                           clockInTime TIME NOT NULL,
                           clockOutTime TIME NOT NULL,
                           emp_id VARCHAR(10),
                           FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE performanceReview(
                                  review_id VARCHAR(10) PRIMARY KEY,
                                  emp_id VARCHAR(10),
                                  comments VARCHAR(100) NOT NULL,
                                  ratings ENUM ('Average','Good','Excellent') NOT NULL,
                                  reviewDate DATE,
                                  FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE leaveRequest(
                             request_id VARCHAR(10) PRIMARY KEY,
                             emp_id VARCHAR(10),
                             leaveType VARCHAR(25) NOT NULL,
                             startDate DATE NOT NULL,
                             endDate DATE NOT NULL,
                             status ENUM ('Approved','Not Approved') NOT NULL,
                             requestDate DATE NOT NULL,
                             FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE compensationAndBenefits(
                                        compensation_id VARCHAR(10) PRIMARY KEY,
                                        emp_id VARCHAR(10),
                                        salary DOUBLE(12,2) NOT NULL,
                                        bonus DOUBLE(12,2) NOT NULL,
                                        benefits VARCHAR(100) NOT NULL,
                                        status ENUM ('Approved','Not Approved') NOT NULL,
                                        FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE exitInterview(
                              interview_id VARCHAR(10) PRIMARY KEY,
                              emp_id VARCHAR(10),
                              reasonForLeaving VARCHAR(100) NOT NULL,
                              feedBack VARCHAR(50) NOT NULL,
                              interviewer VARCHAR(20) NOT NULL,
                              interviewDate DATE NOT NULL,
                              FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE trainingProgram(
                                program_id VARCHAR(10) PRIMARY KEY,
                                name VARCHAR(20) NOT NULL,
                                description VARCHAR(50) NOT NULL,
                                trainers VARCHAR(50) NOT NULL,
                                duration VARCHAR(20) NOT NULL
);

CREATE TABLE trainingEnrollment(
                                   enrollment_id VARCHAR(10) PRIMARY KEY,
                                   emp_id VARCHAR(10),
                                   program_id VARCHAR(10),
                                   enrollmentDate DATE NOT NULL,
                                   status ENUM('Completed','Incompleted') NOT NULL,
                                   FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE,
                                   FOREIGN KEY (program_id) REFERENCES trainingProgram(program_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE onboardingChecklist(
                                    checklist_id VARCHAR(10) PRIMARY KEY,
                                    onboardingTasks VARCHAR(100) NOT NULL,
                                    dueDate DATE NOT NULL,
                                    status ENUM('Completed','Incompleted') NOT NULL
);

CREATE TABLE employeeOnboardingChecklist(
                                            empOnboarding_id VARCHAR(10) PRIMARY KEY,
                                            emp_id VARCHAR(10),
                                            checklist_id VARCHAR(10),
                                            dueDate DATE NOT NULL,
                                            status ENUM('Completed','Incompleted') NOT NULL,
                                            FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE,
                                            FOREIGN KEY (checklist_id) REFERENCES onboardingChecklist(checklist_id) ON UPDATE CASCADE ON DELETE CASCADE
);