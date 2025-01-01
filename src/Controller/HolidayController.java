package Controller;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import Model.Employee;
import Model.Holiday;
import Model.HolidayModel;
import Model.HolidayType;
import Model.Role;
import View.HolidayView;

public class HolidayController {
    private static HolidayModel holidayModel;
    private static HolidayView holidayView;
    private boolean isDeselecting = false;
    private Employee employeeLogged;

    public HolidayController(HolidayModel model, HolidayView view, Employee employee) {
        this.employeeLogged = employee;
        this.holidayModel = model;
        this.holidayView = view;
        setEmployeesInComboBox();
        holidayView.getAjouterButton().addActionListener(e -> this.ajouterHoliday());
        holidayView.getAfficherButton().addActionListener(e -> {
            this.deselectHoliday();
            this.afficherHoliday();
        });
        holidayView.getModifierButton().addActionListener(e -> this.ModifierHoliday());
        holidayView.getSupprimerButton().addActionListener(e -> this.supprimerHoliday());
        holidayView.getTable().getSelectionModel().addListSelectionListener(e -> this.setHolidayInformations());
        holidayView.getDeselectButton().addActionListener(e -> this.deselectHoliday());
        if (employeeLogged != null && (employeeLogged.getRole().equals(Role.ADMIN) || employeeLogged.getRole().equals(Role.MANAGER))) {
            this.afficherHoliday();
        }
        if (employeeLogged != null && employeeLogged.getRole().equals(Role.EMPLOYEE)) {
            this.afficherHolidayLogged();
        }
    }

    public void ajouterHoliday() {
        JComboBox<String> nom = holidayView.getNomEmployeComboBox();
        int Employeeid = Integer.parseInt(nom.getSelectedItem().toString().split(" - ")[0]);
        HolidayType type = (HolidayType) holidayView.getTypeComboBox().getSelectedItem();
        String dateDebut = holidayView.getDateDebut();
        String dateFin = holidayView.getDateFin();
        Holiday holiday = new Holiday(1, Employeeid, type, dateDebut, dateFin);

        Employee employee = holidayModel.FindById(Employeeid);
        if (employee != null) {
            holidayModel.ajouterHoliday(holiday, employee);  // Appel à ajouterHoliday
            this.viderLesChamps();
            this.afficherHoliday();
        } else {
            HolidayView.fail("Employé introuvable.");
        }
    }

    public void afficherHoliday() {
        List<Holiday> holidays = holidayModel.afficher();
        if (holidays != null && !holidays.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) holidayView.getHolidayTable().getModel();
            model.setRowCount(0); // Réinitialiser les lignes
            for (Holiday holiday : holidays) {
                Employee employee = holidayModel.FindById(holiday.getIdEmployee());
                String employeeName = (employee != null) ? employee.getNom() + " " + employee.getPrenom() : "Employé inconnu";
                model.addRow(new Object[]{holiday.getId(), employeeName, holiday.getType(), holiday.getStart(), holiday.getEnd()});
            }
        }
    }

    public void ModifierHoliday() {
        int selectedRow = holidayView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            HolidayView.fail("Veuillez sélectionner une ligne.");
            return;
        }

        int idHoliday = Integer.parseInt(holidayView.getTable().getModel().getValueAt(selectedRow, 0).toString());
        Holiday oldHoliday = holidayModel.FindHolidayById(idHoliday);
        if (oldHoliday != null) {
            Holiday updatedHoliday = new Holiday();
            updatedHoliday.setId(idHoliday);
            updatedHoliday.setIdEmployee(Integer.parseInt(holidayView.getNomEmployeComboBox().getSelectedItem().toString().split(" - ")[0]));
            updatedHoliday.setType((HolidayType) holidayView.getTypeComboBox().getSelectedItem());
            updatedHoliday.setStart(holidayView.getDateDebut());
            updatedHoliday.setEnd(holidayView.getDateFin());

            holidayModel.ModifierHoliday(updatedHoliday, oldHoliday);
            this.afficherHoliday();
            this.deselectHoliday();
        } else {
            HolidayView.fail("Holiday introuvable.");
        }
    }

    public void supprimerHoliday() {
        int selectedRow = holidayView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            HolidayView.fail("Veuillez Sélectionner une ligne.");
            return;
        } else {
            int idHoliday = Integer.parseInt(holidayView.getTable().getModel().getValueAt(selectedRow, 0).toString());
            Holiday oldHoliday = holidayModel.FindHolidayById(idHoliday);
            if (oldHoliday != null) {
                holidayModel.supprimerHoliday(oldHoliday);
                this.afficherHoliday();
                this.deselectHoliday();
            } else {
                HolidayView.fail("Holiday introuvable.");
            }
        }
    }

    public static void setEmployeesInComboBox() {
        List<Employee> employees = holidayModel.afficherEmployee();
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (Employee e : employees) {
            comboBoxModel.addElement(e.getId() + " - " + e.getNom() + " " + e.getPrenom());
        }
        holidayView.getNomEmployeComboBox().setModel(comboBoxModel);
    }

    public void setHolidayInformations() {
        if (isDeselecting) return;

        int selectedRow = holidayView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int id = Integer.parseInt(holidayView.getTable().getModel().getValueAt(selectedRow, 0).toString());
        Holiday holiday = holidayModel.FindHolidayById(id);
        if (holiday != null) {
            Employee employee = holidayModel.FindById(holiday.getIdEmployee());
            if (employee != null) {
                holidayView.getNomEmployeComboBox().setSelectedItem(employee.getId() + " - " + employee.getNom() + " " + employee.getPrenom());
                holidayView.getNomEmployeComboBox().setEnabled(false);
                holidayView.getDeselectButton().setVisible(true);
                holidayView.getTypeComboBox().setSelectedItem(holiday.getType());
                holidayView.setDateDebut(holiday.getStart());
                holidayView.setDateFin(holiday.getEnd());
            } else {
                HolidayView.fail("Employé introuvable.");
            }
        } else {
            HolidayView.fail("Holiday introuvable.");
        }
    }

    public void deselectHoliday() {
        isDeselecting = true;
        holidayView.getNomEmployeComboBox().setEnabled(true);
        holidayView.getDeselectButton().setVisible(false);
        this.viderLesChamps();
        holidayView.getTable().clearSelection();
        isDeselecting = false;
    }

    public void viderLesChamps() {
        holidayView.setDateDebut("YYYY-MM-DD");
        holidayView.setDateFin("YYYY-MM-DD");
    }

    public void afficherHolidayLogged() {
        if (employeeLogged != null) {
            List<Holiday> holidays = HolidayModel.afficherHolidaysLogged(employeeLogged.getId());
            DefaultTableModel model = (DefaultTableModel) holidayView.getHolidayTable().getModel();
            model.setRowCount(0);
            if (holidays != null && !holidays.isEmpty()) {
                for (Holiday holiday : holidays) {
                    model.addRow(new Object[]{holiday.getId(), employeeLogged.getNom() + " " + employeeLogged.getPrenom(), holiday.getType(), holiday.getStart(), holiday.getEnd()});
                }
            } else {
                holidayView.getTable().setModel(new DefaultTableModel(new Object[]{"Aucun holiday à afficher"}, 0));
            }
        } else {
            HolidayView.fail("Utilisateur non connecté.");
        }
    }
}
