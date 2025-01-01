package View;

import java.awt.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Model.Poste;
import Model.Role;

public class EmployeeViewEmployee extends EmployeeView{
    protected static final EmployeeViewEmployee INSTANCE = new EmployeeViewEmployee();
    public EmployeeViewEmployee() {
        super();
        GeneralUp.remove(SalaireLabel);
        GeneralUp.remove(Salaire);
        GeneralUp.remove(RoleLabel);
        GeneralUp.remove(RoleComboBox);
        GeneralUp.remove(PosteLabel);
        GeneralUp.remove(PosteComboBox);
        GeneralUp.setLayout(new GridLayout(4, 2, 0, 15));
        Ajouter.setVisible(false);
        Supprimer.setVisible(false);
        getCreerCompteButton().setVisible(false);
    }
    public static EmployeeViewEmployee getInstance() {
        return INSTANCE;
    }
}