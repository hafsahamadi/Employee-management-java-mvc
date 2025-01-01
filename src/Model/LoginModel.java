package Model;

import DAO.LoginDAOImpl;
import View.LoginView;

public class LoginModel {
    private LoginDAOImpl dao;
    private static boolean isAdmin = false;
    public LoginModel(LoginDAOImpl dao) {
        this.dao = dao;
    }
    public Employee Login(String username, String password) {
        int CheckId;
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            LoginView.LoginFail("Veuillez remplir tous les champs");
            return null;
        }
        if(password.length() < 8) {
            LoginView.LoginFail("Le mot de passe doit contenir au moins 8 caractères.");
            return null;
        }
        CheckId = dao.Login(username, password);
        if (CheckId == -1) {
            LoginView.LoginFail("Nom d'utilisateur ou mot de passe incorrect.");
            return null;
        }
        Employee employee = dao.FindById(CheckId);
        if(employee.getRole() == Role.ADMIN){
            isAdmin = true;
        };
        return employee;
    }

    public static boolean getIsAdmin(){
        return isAdmin;
    }
}