package Controller;

import DAO.EmployeeDAOImpl;
import DAO.HolidayDAOImpl;
import Model.Employee;
import Model.EmployeeModel;
import Model.HolidayModel;
import Model.LoginModel;
import Model.Role;
import View.PanelsView;
import View.EmployeeView;
import View.EmployeeViewEmployee;
import View.EmployeeViewManager;
import View.HolidayView;
import View.HolidayViewEmployee;
import View.HolidayViewManager;
import View.LoginView;

public class LoginController {
    private LoginModel loginModel;
    private LoginView loginView;

    public LoginController(LoginModel loginModel, LoginView loginView) {
        this.loginModel = loginModel;
        this.loginView = loginView;
        this.loginView.getLoginButton().addActionListener(e -> this.Login());
    }

    private void Login() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        Employee employee = loginModel.Login(username, password);
        Role role = null;
        try {
            role = employee.getRole();
        } catch (NullPointerException e) {
            return;
        }
        if (role == Role.ADMIN) {
            loginView.dispose();
            EmployeeController employeeController = new EmployeeController(new EmployeeModel(new EmployeeDAOImpl()), EmployeeView.getInstance(),employee);
            HolidayController holidayController = new HolidayController(new HolidayModel(new HolidayDAOImpl()), HolidayView.getInstance(),employee);
            PanelsView.getInstance(EmployeeView.getInstance(),HolidayView.getInstance());
        }
        if (role == Role.MANAGER) {
            loginView.dispose();
            EmployeeController employeeController = new EmployeeController(new EmployeeModel(new EmployeeDAOImpl()), EmployeeViewManager.getInstance(),employee);
            HolidayController holidayController = new HolidayController(new HolidayModel(new HolidayDAOImpl()), HolidayViewManager.getInstance(),employee);
            PanelsView.getInstance(EmployeeViewManager.getInstance(),HolidayViewManager.getInstance());
            HolidayView.getInstance().dispose();
            EmployeeView.getInstance().dispose();
        }
        if (role == Role.EMPLOYEE) {
            loginView.dispose();
            EmployeeController employeeController = new EmployeeController(new EmployeeModel(new EmployeeDAOImpl()), EmployeeViewEmployee.getInstance(),employee);
            HolidayController holidayController = new HolidayController(new HolidayModel(new HolidayDAOImpl()), HolidayViewEmployee.getInstance(),employee);
            PanelsView.getInstance(EmployeeViewEmployee.getInstance(),HolidayViewEmployee.getInstance());
            HolidayView.getInstance().dispose();
            EmployeeView.getInstance().dispose();
        }
    }
}