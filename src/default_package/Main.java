package default_package;

import Controller.LoginController;
import DAO.LoginDAOImpl;
import Model.LoginModel;
import View.LoginView;

public class Main {

    public static void main(String[] args) {
        LoginController loginController = new LoginController(new LoginModel(new LoginDAOImpl()), LoginView.getInstance());
    }
}