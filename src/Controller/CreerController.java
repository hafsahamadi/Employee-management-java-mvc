package Controller;

import DAO.CreerDAOImpl;
import Model.Creer;
import Model.CreerModel;
import View.CreerView;
import View.EmployeeView;

public class CreerController {
    private CreerView creerView;
    private CreerModel creerModel;

    public CreerController(CreerModel creerModel, CreerView creerView) {
        this.creerView = creerView;
        this.creerModel = creerModel;
        
        this.creerView.getCreateAccountButton().addActionListener(e -> CreateAccountCheck());
    }

    public CreerController() {
        if (EmployeeController.getId() != -1) {
            CreerModel cmodel = new CreerModel(new CreerDAOImpl());
            CreerView cview = new CreerView();
            new CreerController(cmodel, cview);
        } else {
            EmployeeView.ModifierFail("Veuillez choisir un employé.");
        }
    }

    private void CreateAccountCheck() {
        boolean isSuccess = creerCompte();
        if (isSuccess) {
            EmployeeController.deselectEmployee();
            creerView.dispose();
        }
    }

    public boolean creerCompte() {
        int id = EmployeeController.getId();
        String username = creerView.getUsername().replaceAll("\\s", "_");
        String password = creerView.getPassword();
        Creer newAccount = new Creer(username, password);
        return creerModel.creerCompte(id, newAccount);
    }
}