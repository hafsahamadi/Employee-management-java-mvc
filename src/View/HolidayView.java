package View;

import javax.swing.*;
import java.awt.*;

import javax.swing.table.DefaultTableModel;
import Model.HolidayType;

public class HolidayView extends JFrame {
    protected static final HolidayView INSTANCE = new HolidayView();
    protected JPanel generalPanel = new JPanel();
    protected JLabel nomEmployeLabel = new JLabel("Nom de l'employé");
    protected JComboBox<String> nomEmployeComboBox = new JComboBox<>();
    protected JLabel typeLabel = new JLabel("Type");
    protected JComboBox<HolidayType> typeComboBox = new JComboBox<>(HolidayType.values());
    protected JLabel dateDebutLabel = new JLabel("Date de début");
    protected JTextField dateDebut = new JTextField("YYYY-MM-DD");
    protected JLabel dateFinLabel = new JLabel("Date de fin");
    protected JTextField dateFin = new JTextField("YYYY-MM-DD");
    protected DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Id","Employé","Type","Date début","Date fin"}, 0){
        @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
    };
    protected JTable holidayTable = new JTable(tableModel);
    protected JScrollPane tableScrollPane = new JScrollPane(holidayTable);
    protected JButton ajouterButton = new JButton("Ajouter");
    protected JButton modifierButton = new JButton("Modifier");
    protected JButton supprimerButton = new JButton("Supprimer");
    protected JButton afficherButton = new JButton("Afficher");
    protected JButton deselectButton = new JButton("Désélectionner");
    protected JPanel inputPanel = new JPanel();
    protected JPanel buttonPanel = new JPanel();

    public HolidayView() {
        setTitle("Gestion des holidays");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(930, 520);
        setLocationRelativeTo(null);
        setVisible(true);
        
        generalPanel.setLayout(new BorderLayout());
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        inputPanel.add(nomEmployeLabel);
        inputPanel.add(nomEmployeComboBox);
        inputPanel.add(typeLabel);
        inputPanel.add(typeComboBox);
        inputPanel.add(dateDebutLabel);
        inputPanel.add(dateDebut);
        inputPanel.add(dateFinLabel);
        inputPanel.add(dateFin);
        generalPanel.add(inputPanel, BorderLayout.NORTH);
    
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        generalPanel.add(tableScrollPane, BorderLayout.CENTER);
    
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(ajouterButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(afficherButton);
        buttonPanel.add(deselectButton);
        deselectButton.setVisible(false);
        generalPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(generalPanel);
        
        holidayTable.setFillsViewportHeight(true);
        holidayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public JComboBox<String> getNomEmployeComboBox() {
        return nomEmployeComboBox;
    }
    public JComboBox<HolidayType> getTypeComboBox() {
        return typeComboBox;
    }
    public void setTypeComboBox(JComboBox<HolidayType> typeComboBox) {
        this.typeComboBox = typeComboBox;
    }
    public String getDateDebut() {
        return dateDebut.getText();
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut.setText(dateDebut);
    }

    public String getDateFin() {
        return dateFin.getText();
    }

    public void setDateFin(String dateFin) {
        this.dateFin.setText(dateFin);
    }
    public JButton getAfficherButton() {
        return afficherButton;
    }
    public JButton getAjouterButton() {
        return ajouterButton;
    }

    public JButton getModifierButton() {
        return modifierButton;
    }

    public JButton getSupprimerButton() {
        return supprimerButton;
    }
    public JTable getHolidayTable() {
        return holidayTable;
    }
    public static HolidayView getInstance() {
        return INSTANCE;
    }
    public static void success(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void fail(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public JTable getTable() {
        return holidayTable;
    }
    public JButton getDeselectButton() {
        return deselectButton;
    }

    public void setDeselectButton(JButton deselectButton) {
        this.deselectButton = deselectButton;
    }
}