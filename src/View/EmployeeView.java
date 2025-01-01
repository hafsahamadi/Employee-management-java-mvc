package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.Employee;
import Model.Poste;
import Model.Role;
import java.awt.*;

public class EmployeeView extends JFrame {
    private static EmployeeView INSTANCE; // Déclare l'instance comme nullable
    protected JPanel General = new JPanel();
    protected JPanel GeneralUp = new JPanel();
    protected JPanel GeneralDown = new JPanel();
    protected JPanel ListContainer = new JPanel();
    protected JPanel ButtonsContainer = new JPanel();
    protected DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Id","Nom", "Prenom", "Email", "Salaire", "Phone", "Role", "Poste", "Holiday Balance"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    protected JTable Tableau = new JTable(tableModel);
    protected JButton Ajouter = new JButton("Ajouter");
    protected JButton Modifier = new JButton("Modifier");
    protected JButton Supprimer = new JButton("Supprimer");
    protected JButton Afficher = new JButton("Afficher");
    protected JButton CreerCompte = new JButton("Créer un compte");
    private JButton deselectButton = new JButton("Désélectionner");
    protected JButton Import = new JButton("Importer"); 
    protected JButton Export = new JButton("Exporter"); 

    protected JLabel NomLabel;
    protected JTextField Nom;
    protected JLabel PrenomLabel;
    protected JTextField Prenom;
    protected JLabel EmailLabel;
    protected JTextField Email;
    protected JLabel TelephoneLabel;
    protected JTextField Telephone;
    protected JLabel SalaireLabel;
    protected JTextField Salaire;
    protected JLabel RoleLabel;
    protected JComboBox<Role> RoleComboBox;
    protected JLabel PosteLabel;
    protected JComboBox<Poste> PosteComboBox;

    public EmployeeView() {
        setTitle("Gestion des employés");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(930, 520);
        setLocationRelativeTo(null);
        add(General);
        
        Import.addActionListener(e -> {
            // Action à réaliser lors de l'import
        });

        Export.addActionListener(e -> {
            // Action à réaliser lors de l'export
        });

        General.setLayout(new BorderLayout());
        General.add(GeneralUp, BorderLayout.NORTH);
        General.add(GeneralDown, BorderLayout.CENTER);
        
        GeneralUp.setLayout(new GridLayout(7,2));
        GeneralUp.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        NomLabel = new JLabel("Nom");
        Nom = new JTextField();
        GeneralUp.add(NomLabel);
        GeneralUp.add(Nom);
        PrenomLabel = new JLabel("Prénom");
        Prenom = new JTextField();
        GeneralUp.add(PrenomLabel);
        GeneralUp.add(Prenom);
        EmailLabel = new JLabel("Email");
        Email = new JTextField();
        GeneralUp.add(EmailLabel);
        GeneralUp.add(Email);
        TelephoneLabel = new JLabel("Téléphone");
        Telephone = new JTextField();
        GeneralUp.add(TelephoneLabel);
        GeneralUp.add(Telephone);
        SalaireLabel = new JLabel("Salaire");
        Salaire = new JTextField();
        GeneralUp.add(SalaireLabel);
        GeneralUp.add(Salaire);
        RoleLabel = new JLabel("Role");
        RoleComboBox = new JComboBox<>(Role.values());
        GeneralUp.add(RoleLabel);
        GeneralUp.add(RoleComboBox);
        PosteLabel = new JLabel("Poste");
        PosteComboBox = new JComboBox<>(Poste.values());
        GeneralUp.add(PosteLabel);
        GeneralUp.add(PosteComboBox);

        GeneralDown.setLayout(new BorderLayout());
        GeneralDown.add(ListContainer, BorderLayout.CENTER);
        ListContainer.setLayout(new FlowLayout());
        Dimension preferredSize = new Dimension(EmployeeView.this.getWidth() - 50,500);
        Tableau.setPreferredScrollableViewportSize(preferredSize);
        Tableau.setFillsViewportHeight(true);
        ListContainer.add(new JScrollPane(Tableau));
        
        GeneralDown.add(ButtonsContainer, BorderLayout.SOUTH);
        ButtonsContainer.setLayout(new FlowLayout());
        ButtonsContainer.add(Ajouter);
        ButtonsContainer.add(Modifier);
        ButtonsContainer.add(Supprimer);
        ButtonsContainer.add(Afficher);
        ButtonsContainer.add(CreerCompte);
        ButtonsContainer.add(deselectButton);
        ButtonsContainer.add(Import);
        ButtonsContainer.add(Export);

        deselectButton.setVisible(false);
        setVisible(true);
    }

    // Les méthodes de message pour l'affichage
    public static void AjouterSuccess(Employee employee){
        JOptionPane.showMessageDialog(null, "L'employé " + employee.getNom() + " " + employee.getPrenom() + " a été ajouté avec succès");
    }
    
    public static void AjouterFail(String message){
        JOptionPane.showMessageDialog(null, message);
    }
    
    public static void SupprimerSuccess(){
        JOptionPane.showMessageDialog(null, "L'employé a bien éte supprimé.");
    }
    
    public static void SupprimerFail(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    public static void ModifierSuccess(){
        JOptionPane.showMessageDialog(null, "L'employé a bien éte modifié.");
    }

    public static void ModifierFail(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    // Ajouter la méthode AfficherFail
    public static void AfficherFail(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    protected void CacherColumn(int index){
        Tableau.getColumnModel().getColumn(index).setMinWidth(0);
        Tableau.getColumnModel().getColumn(index).setMaxWidth(0);
        Tableau.getColumnModel().getColumn(index).setWidth(0);
    }

    public static boolean SupprimerConfirmation(){
        int choice = JOptionPane.showOptionDialog(null, "Êtes-vous sûr de supprimer cet employé?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Oui", "Non"}, "Non");
        return choice == JOptionPane.YES_OPTION;
    }

    public JTable getTable() {
        return Tableau;
    }

    public JButton getAjouterButton() {
        return Ajouter;
    }

    public JButton getModifierButton() {
        return Modifier;
    }

    public JButton getSupprimerButton() {
        return Supprimer;
    }

    public JButton getAfficherButton() {
        return Afficher;
    }

    public JButton getCreerCompteButton() {
        return CreerCompte;
    }

    public JTextField getNomField() {
        return Nom;
    }

    public void setNomField(JTextField nomField) {
        Nom = nomField;
    }

    public JTextField getPrenomField() {
        return Prenom;
    }

    public void setPrenomField(JTextField prenomField) {
        Prenom = prenomField;
    }

    public JTextField getSalaireField() {
        return Salaire;
    }

    public void setSalaireField(JTextField salaireField) {
        Salaire = salaireField;
    }

    public JTextField getEmailField() {
        return Email;
    }

    public void setEmailField(JTextField emailField) {
        Email = emailField;
    }

    public JTextField getPhoneField() {
        return Telephone;
    }

    public void setPhoneField(JTextField phoneField) {
        Telephone = phoneField;
    }

    public JComboBox<Role> getRoleComboBox() {
        return RoleComboBox;
    }

    public void setRoleComboBox(JComboBox<Role> roleComboBox) {
        RoleComboBox = roleComboBox;
    }

    public JComboBox<Poste> getPosteComboBox() {
        return PosteComboBox;
    }

    public void setPosteComboBox(JComboBox<Poste> posteComboBox) {
        PosteComboBox = posteComboBox;
    }

    // Méthode singleton : vérifier si l'instance existe déjà
    public static EmployeeView getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmployeeView(); // Si l'instance est null, créez une nouvelle instance
        }
        return INSTANCE;
    }

    public JButton getDeselectButton() {
        return deselectButton;
    }

    public void setDeselectButton(JButton deselectButton) {
        this.deselectButton = deselectButton;
    }
}
