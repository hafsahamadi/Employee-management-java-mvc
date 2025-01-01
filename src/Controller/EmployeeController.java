package Controller;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import Utilities.Utils;

import Model.Employee;
import Model.EmployeeModel;
import Model.LoginModel;
import Model.Poste;
import Model.Role;
import View.EmployeeView;

public class EmployeeController {
    protected EmployeeModel employeeModel;
    protected static EmployeeView employeeView;
    private static boolean isDeselecting = false;
    private Employee employeelogged;
    public EmployeeController(EmployeeModel employeeModel, EmployeeView employeeView,Employee employee) {
        this.employeelogged = employee;
        this.employeeModel = employeeModel;
        EmployeeController.employeeView = employeeView;
        EmployeeController.employeeView.getAjouterButton().addActionListener(e -> this.ajouterEmployee());
        EmployeeController.employeeView.getAfficherButton().addActionListener(e -> {
            if (employeeView.getNomField().getText().isEmpty() && employeeView.getPrenomField().getText().isEmpty() && employeeView.getSalaireField().getText().isEmpty() && employeeView.getEmailField().getText().isEmpty() && employeeView.getPhoneField().getText().isEmpty()) {
                if(employeelogged.getRole().equals(Role.ADMIN) || employeelogged.getRole().equals(Role.MANAGER)){
                    this.afficherEmployee();
                }
                if(employeelogged.getRole().equals(Role.EMPLOYEE)){
                    this.afficherEmployeeLogged();
                }
            }
        });
        EmployeeController.employeeView.getSupprimerButton().addActionListener(e -> this.supprimerEmployee());
        EmployeeController.employeeView.getModifierButton().addActionListener(e -> this.updateEmployee());
        EmployeeController.employeeView.getCreerCompteButton().addActionListener(e -> new CreerController());
        EmployeeController.employeeView.getTable().getSelectionModel().addListSelectionListener(e -> this.setEmployeeInformations());
        EmployeeController.employeeView.getDeselectButton().addActionListener(e -> EmployeeController.deselectEmployee());
        if(employeelogged.getRole().equals(Role.ADMIN) || employeelogged.getRole().equals(Role.MANAGER)){
            this.afficherEmployee();
        }
        if(employeelogged.getRole().equals(Role.EMPLOYEE)){
            this.afficherEmployeeLogged();
        }
    }
    public void ajouterEmployee() {
        String nom  = employeeView.getNomField().getText();
        String prenom = employeeView.getPrenomField().getText();
        String salaire = employeeView.getSalaireField().getText();
        String email = employeeView.getEmailField().getText();
        String phone = employeeView.getPhoneField().getText();
        Role role = (Role) employeeView.getRoleComboBox().getSelectedItem();
        Poste poste = (Poste) employeeView.getPosteComboBox().getSelectedItem();
        boolean ajouter = employeeModel.ajouterEmployee(nom, prenom, salaire, email, phone, role , poste);
        if(ajouter) {
            this.afficherEmployee();
        }
    }
    public void afficherEmployee() {
        List<Employee> employees = employeeModel.afficherEmployee();
        DefaultTableModel tableModel = (DefaultTableModel) employeeView.getTable().getModel();
        tableModel.setRowCount(0);
        for(Employee e : employees) {
            tableModel.addRow(new Object[]{e.getId(), e.getNom(), e.getPrenom(), e.getEmail(), e.getSalaire(), e.getPhone(), e.getRole(), e.getPoste(),e.getHolidayBalance()});
        }
    }

    public void supprimerEmployee() {
        int selectedRow = employeeView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = Integer.parseInt(employeeView.getTable().getModel().getValueAt(selectedRow, 0).toString());
                employeeModel.supprimerEmployee(id);
                this.deselectEmployee();
                this.afficherEmployee();
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        } else {
            EmployeeView.SupprimerFail("Veuillez choisir un employé.");
        }
        this.afficherEmployee();
    }
    public void updateEmployee() {
        int selectedRow = employeeView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = Integer.parseInt(employeeView.getTable().getModel().getValueAt(selectedRow, 0).toString());
                String nom = employeeView.getNomField().getText();
                String prenom = employeeView.getPrenomField().getText();
                String email = employeeView.getEmailField().getText();
                double salaire = Utils.parseDouble(employeeView.getSalaireField().getText());
                String phone = employeeView.getPhoneField().getText();
                Role role = (Role) (employeeView.getRoleComboBox().getSelectedItem());
                Poste poste = (Poste) employeeView.getPosteComboBox().getSelectedItem();
                Employee employeeToUpdate = employeeModel.findById(id);
                if (employeeToUpdate != null) {
                    employeeModel.updateEmployee(employeeToUpdate,id, nom, prenom, email, salaire, phone, role, poste);
                    this.deselectEmployee();
                    if(employeelogged.getRole().equals(Role.ADMIN) || employeelogged.getRole().equals(Role.MANAGER)){
                        this.afficherEmployee();
                    }
                    if(employeelogged.getRole().equals(Role.EMPLOYEE)){
                        this.afficherEmployeeLogged();
                    }
                } else {
                    EmployeeView.ModifierFail("L'employé avec l'ID spécifié n'existe pas.");
                }
            } catch (NumberFormatException e) {
                EmployeeView.ModifierFail("Erreur lors de la mise à jour de l'employé.");
            }
        }else{
            EmployeeView.ModifierFail("Veuillez choisir un employé.");
        }
    }
    
    public static int getId(){
        int selectedRow = employeeView.getTable().getSelectedRow();
        int id=-1;
        if (selectedRow != -1) {
            try {
                id = Integer.parseInt(employeeView.getTable().getModel().getValueAt(selectedRow, 0).toString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        }
        return id;
    }
    public static void viderLesChamps(){
        boolean check = LoginModel.getIsAdmin();
        check = true;////// BINMA 9ADINA HOLIDAYS O LOGIN 
        if(check == true){
            employeeView.getNomField().setText("");
            employeeView.getPrenomField().setText("");
            employeeView.getSalaireField().setText("");
            employeeView.getEmailField().setText("");
            employeeView.getPhoneField().setText("");
            employeeView.getRoleComboBox().setSelectedIndex(-1);
            employeeView.getPosteComboBox().setSelectedIndex(-1);
            return;
        }
    }
    public void setEmployeeInformations() {
        if (isDeselecting) return;
        int selectedRow = employeeView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        int id = Integer.parseInt(employeeView.getTable().getModel().getValueAt(selectedRow, 0).toString());
        Employee employee = employeeModel.findById(id);
        employeeView.getNomField().setText(employee.getNom());
        employeeView.getPrenomField().setText(employee.getPrenom());
        employeeView.getSalaireField().setText(String.valueOf(employee.getSalaire()));
        employeeView.getEmailField().setText(employee.getEmail());
        employeeView.getPhoneField().setText(employee.getPhone());
        employeeView.getRoleComboBox().setSelectedItem(employee.getRole());
        employeeView.getPosteComboBox().setSelectedItem(employee.getPoste());
        employeeView.getDeselectButton().setVisible(true);
    }
    private void handleExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers CSV", "csv"));

        if (fileChooser.showSaveDialog(employeeView) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                }

                List<Employee> employees = employeeModel.findAll();
                employeeModel.exportData(filePath, employees);
                employeeView.showSuccessMessage("Exportation réussie !");
            } catch (Exception ex) {
                employeeView.showErrorMessage("Erreur lors de l'exportation : " + ex.getMessage());
            }
        }
    }
    private void handleImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers CSV", "txt"));

        if (fileChooser.showOpenDialog(employeeView) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                employeeModel.importData(filePath);
                employeeView.showSuccessMessage("Importation réussie !");
            } catch (Exception ex) {
                employeeView.showErrorMessage("Erreur lors de l'importation : " + ex.getMessage());
            }
        }
    }
    public void importerFichier() {
        handleImport(); // Appelle la logique d'importation existante
    }

    public void exporterFichier() {
        handleExport(); // Appelle la logique d'exportation existante
    }

    public static void deselectEmployee() {
        isDeselecting = true;
        employeeView.getNomField().setText("");
        employeeView.getPrenomField().setText("");
        employeeView.getSalaireField().setText("");
        employeeView.getEmailField().setText("");
        employeeView.getPhoneField().setText("");
        employeeView.getRoleComboBox().setSelectedIndex(-1);
        employeeView.getPosteComboBox().setSelectedIndex(-1);
        employeeView.getDeselectButton().setVisible(false);
        employeeView.getTable().clearSelection();
        isDeselecting = false;
    }
    public void afficherEmployeeLogged() {
        Employee employeeloggeddb = employeeModel.findById(employeelogged.getId());
        DefaultTableModel tableModel = (DefaultTableModel) employeeView.getTable().getModel();
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{employeeloggeddb.getId(), employeeloggeddb.getNom(), employeeloggeddb.getPrenom(), employeeloggeddb.getEmail(), employeeloggeddb.getSalaire(), employeeloggeddb.getPhone(), employeeloggeddb.getRole(), employeeloggeddb.getPoste(),employeeloggeddb.getHolidayBalance()});
    }
}