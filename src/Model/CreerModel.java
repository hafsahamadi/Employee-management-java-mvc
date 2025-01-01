package Model;

import DAO.CreerDAOImpl;
import View.CreerView;

public class CreerModel {
    private CreerDAOImpl dao;
    public CreerModel(CreerDAOImpl dao) {
        this.dao = dao;
    }
    public boolean creerCompte(int id, Creer newAccount){
        if (newAccount.getUsername().trim().isEmpty() || newAccount.getPassword().trim().isEmpty()) {
            CreerView.CreerCompteFail("Veuillez remplir tous les champs.");
            return false;
        }else if (newAccount.getPassword().length() < 8) {
            CreerView.CreerCompteFail("Le mot de passe doit contenir au moins 8 caractères.");
            return false;
        }
        return dao.creerCompte(id, newAccount);
    }
}