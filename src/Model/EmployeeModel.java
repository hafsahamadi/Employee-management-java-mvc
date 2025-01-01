package Model;

import java.io.File;
import java.util.List;

import DAO.EmployeeDAOImpl;
import Utilities.Utils;
import View.EmployeeView;

public class EmployeeModel {
    private EmployeeDAOImpl dao;
    public EmployeeModel(EmployeeDAOImpl dao) {
        this.dao = dao;
    }

    public boolean ajouterEmployee(String nom, String prenom, String salaire, String email, String phone, Role role, Poste poste) {
        double salaireDouble = Utils.parseDouble(salaire);
        if(nom.trim().isEmpty() || prenom.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || salaireDouble == 0) {
            EmployeeView.AjouterFail("Veuillez remplir tous les champs.");
            return false;
        }
        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            EmployeeView.AjouterFail("Veuillez entrer une adresse email valide.");
            return false;
        }
        if(!phone.matches("^0\\d{9}$")) {
            EmployeeView.AjouterFail("Le numéro de téléphone doit contenir 10 chiffres");
            return false;
        }
        
        if(salaireDouble < 0 ){
            EmployeeView.AjouterFail("Le salaire doit être un nombre positif");
            return false;
        }
        Employee employee = new Employee(0, nom, prenom, salaireDouble, email, phone, role, poste,25);
        return dao.ajouter(employee);
    }

    public List<Employee> afficherEmployee() {
        return dao.afficher();
    }
    public void supprimerEmployee(int id) {
        if(EmployeeView.SupprimerConfirmation()){
            dao.supprimer(id);
        }
        return;
    }
    public Employee findById(int id) {
        return dao.findById(id);
    }
    public void updateEmployee(Employee employee, int id, String nom, String prenom, String email, double salaire, String phone, Role role, Poste poste) {
        if (nom.trim().isEmpty() && prenom.trim().isEmpty() && email.trim().isEmpty() && phone.trim().isEmpty() && salaire == 0 && role == null && poste == null) {
            EmployeeView.ModifierFail("Veuillez remplir au moins un champ.");
            return;
        }

        // Récupère l'employé existant de la base de données avant de modifier
        Employee existingEmployee = dao.findById(id);
        if (existingEmployee == null) {
            EmployeeView.ModifierFail("L'employé avec cet ID n'existe pas.");
            return;
        }

        // Mise à jour des champs seulement si de nouvelles valeurs sont fournies
        boolean updated = false;  // Flag pour savoir si une mise à jour a été effectuée
        
        // Mise à jour des valeurs dans l'employé existant
        if (!nom.trim().isEmpty()) {
            existingEmployee.setNom(nom);
            updated = true;
        }
        if (!prenom.trim().isEmpty()) {
            existingEmployee.setPrenom(prenom);
            updated = true;
        }
        if (!email.trim().isEmpty()) {
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                EmployeeView.ModifierFail("Veuillez entrer une adresse email valide.");
                return;
            }
            existingEmployee.setEmail(email);
            updated = true;
        }
        if (salaire != 0) {
            if (salaire < 0) {
                EmployeeView.ModifierFail("Le salaire doit être un nombre positif");
                return;
            }
            existingEmployee.setSalaire(salaire);
            updated = true;
        }
        if (!phone.isEmpty()) {
            if (!phone.matches("^0\\d{9}$")) {
                EmployeeView.ModifierFail("Le numéro de téléphone doit contenir 10 chiffres");
                return;
            }
            existingEmployee.setPhone(phone);
            updated = true;
        }
        if (role != null) {
            existingEmployee.setRole(role);
            updated = true;
        }
        if (poste != null) {
            existingEmployee.setPoste(poste);
            updated = true;
        }

        if (!updated) {
            EmployeeView.ModifierFail("Aucune modification n'a été apportée.");
            return;
        }

        // Si des modifications ont été effectuées, on appelle le DAO pour mettre à jour l'employé dans la base de données
        dao.modifier(existingEmployee, id);
    }
    public boolean checkFileExists(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Le fichier n'existe pas : " + file.getPath());
        }
        return true;
    }
    public boolean checkIsFile(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("Le chemin spécifié n'est pas un fichier : " + file.getPath());
        }
        return true;
    }
    public boolean checkIsReadable(File file) {
        if (!file.canRead()) {
            throw new IllegalArgumentException("Le fichier n'est pas lisible : " + file.getPath());
        }
        return true;
    }


}