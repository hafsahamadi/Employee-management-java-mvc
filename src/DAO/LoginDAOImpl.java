package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.*;

public class LoginDAOImpl implements LoginDAOI {
    Connection connection;
    public LoginDAOImpl() {
        connection = DBConnection.getConnection();
    }
    @Override
    public int Login(String username, String password) {
        String SQL = "SELECT id FROM login WHERE username = ? AND password = ?";
        int id = -1;
        try(PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1,username);
            stmt.setString(2,password);
            try(ResultSet rset = stmt.executeQuery()){
                if(rset.next()) {
                    id = rset.getInt("id");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    @Override
    public Employee FindById(int id) {
        String SQL = "SELECT * FROM employee WHERE id = ?";
        Employee employee = null;
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    String nom = rset.getString("nom");
                    String prenom = rset.getString("prenom");
                    double salaire = rset.getDouble("salaire");
                    String email = rset.getString("email");
                    String phone = rset.getString("phone");
                    String role = rset.getString("role");
                    String poste = rset.getString("poste");
                    int holidayBalance = rset.getInt("holidayBalance");
                    employee = new Employee(id, nom, prenom, salaire, email, phone, Role.valueOf(role), Poste.valueOf(poste),holidayBalance);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}