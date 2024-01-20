package lk.ijse.humanResourceManagement.bo;

import lk.ijse.humanResourceManagement.bo.custom.impl.*;


public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        ATTENDANCE,CHECKLIST,DEPARTMENT,EMPLOYEE,REQUEST,REVIEW,SALARY,PROGRAM,PLACE_ENROLLMENT,SIGN_UP,LOGIN,TRAINING_ENROLLMENT
    }

    public SuperBO getBo(BOTypes types){
        switch (types){
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case CHECKLIST:
                return new ChecklistBOImpl();
            case DEPARTMENT:
                return new DepartmentBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case REQUEST:
                return new RequestBOImpl();
            case REVIEW:
                return new ReviewBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case PROGRAM:
                return new ProgramBOImpl();
            case PLACE_ENROLLMENT:
                return new PlaceEnrollmentBOImpl();
            case SIGN_UP:
                return new SIgnUpBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case TRAINING_ENROLLMENT:
                return new TrainingEnrollmentBOImpl();
            default:
                return null;
        }
    }
}
