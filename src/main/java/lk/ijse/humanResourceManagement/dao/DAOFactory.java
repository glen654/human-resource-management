package lk.ijse.humanResourceManagement.dao;

import lk.ijse.humanResourceManagement.dao.custom.impl.*;


public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        ATTENDANCE,CHECKLIST,DEPARTMENT,EMPLOYEE,PROGRAM,REQUEST,REVIEW,SALARY,USER,TRAINING_ENROLLMENT,QUERY
    }

    public SuperDAO getDao(DAOTypes types){
        switch (types){
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case CHECKLIST:
                return new ChecklistDAOImpl();
            case DEPARTMENT:
                return new DepartmentDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();
            case REQUEST:
                return new RequestDAOImpl();
            case REVIEW:
                return new ReviewDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case USER:
                return new UserDAOImpl();
            case TRAINING_ENROLLMENT:
                return new TrainingEnrollmentDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
