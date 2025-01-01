package View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame{
    private static final LoginView INSTANCE = new LoginView();
    private JPanel General = new JPanel();
    private JPanel InputsContainer = new JPanel();
    private JLabel UsernameLabel = new JLabel("Username");
    private JTextField Username = new JTextField();
    private JLabel PasswordLabel = new JLabel("Password");
    private JTextField Password = new JPasswordField();
    private JPanel ButtonsContainer = new JPanel();
    private JButton Login = new JButton("Login");

    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        add(General);
        General.setLayout(new BorderLayout());
        General.add(InputsContainer, BorderLayout.CENTER);
        InputsContainer.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        General.add(ButtonsContainer, BorderLayout.SOUTH);
        ButtonsContainer.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        InputsContainer.setLayout(new GridLayout(2,2, 10, 10));
        InputsContainer.add(UsernameLabel,0);
        InputsContainer.add(Username,1);
        InputsContainer.add(PasswordLabel,2);
        InputsContainer.add(Password,3);
        ButtonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        Login.setPreferredSize(new Dimension(150, 30));
        Login.setBackground(new Color(41, 144, 59));
        Login.setForeground(Color.WHITE);
        ButtonsContainer.add(Login);
    }
    public String getUsername() {
        return Username.getText();
    }
    public String getPassword() {
        return Password.getText();
    }
    public void setUsername(String username) {
        Username.setText(username);
    }
    public void setPassword(String password) {
        Password.setText(password);
    }
    public static LoginView getInstance() {
        return INSTANCE;
    }
    public JButton getLoginButton() {
        return Login;
    }
    public static void LoginFail(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}