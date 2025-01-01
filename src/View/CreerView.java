package View;

import java.awt.*;
import javax.swing.*;

public class CreerView extends JFrame{

    private JPanel General = new JPanel();
    private JPanel InputsContainer = new JPanel();
    private JLabel UsernameLabel = new JLabel("Username");
    private JTextField Username = new JTextField();
    private JLabel PasswordLabel = new JLabel("Password");
    private JTextField Password = new JPasswordField();
    private JPanel ButtonsContainer = new JPanel();
    private JButton CreateAccount = new JButton("Créer un compte");
    
    
    public CreerView() {
        setTitle("Créer un compte");
        setSize(400, 300);
        setLocationRelativeTo(null);
        add(General);
        General.setLayout(new BorderLayout());
        General.add(InputsContainer, BorderLayout.CENTER);
        InputsContainer.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        General.add(ButtonsContainer, BorderLayout.SOUTH);
        ButtonsContainer.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        InputsContainer.setLayout(new GridLayout(4,2, 10, 10));
        InputsContainer.add(UsernameLabel,0);
        InputsContainer.add(Username,1);
        InputsContainer.add(PasswordLabel,2);
        InputsContainer.add(Password,3);
        ButtonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        ButtonsContainer.add(CreateAccount);
        setVisible(true);
    }
    public String getUsername() {
        return Username.getText();
    }
    
    public void setUsername(String username) {
        Username.setText(username);
    }

    public String getPassword() {
        return Password.getText();
    }

    public void setPassword(String password) {
        Password.setText(password);
    }
    public static void CreerCompteSuccess() {
        JOptionPane.showMessageDialog(null, "Le compte a bien été créé.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void CreerCompteFail(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public JButton getCreateAccountButton() {
        return CreateAccount;
    }
}