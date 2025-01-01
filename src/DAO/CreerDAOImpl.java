package DAO;

import java.sql.*;

import Model.Creer;
import View.CreerView;

public class CreerDAOImpl implements CreerDAOI {
    private Connection connection;
    public CreerDAOImpl() {
        connection = DBConnection.getConnection();
    }
    @Override
    public boolean creerCompte(int id, Creer newAccount) {
        String SQL = "INSERT INTO login (username, password,id) VALUES (?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, newAccount.getUsername());
            stmt.setString(2, newAccount.getPassword());
            stmt.setInt(3, id);
            stmt.executeUpdate();
            CreerView.CreerCompteSuccess();
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate entry")&&e.getMessage().contains("id")){
                CreerView.CreerCompteFail("Cet employé a deja un compte.");
                return false;
            }else{
                if(e.getMessage().contains("Duplicate entry")&&e.getMessage().contains("username")){
                    CreerView.CreerCompteFail("Ce username est deja pris.");
                    return false;
                }else{
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}