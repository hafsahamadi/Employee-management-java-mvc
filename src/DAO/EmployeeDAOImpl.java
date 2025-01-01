package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import Controller.EmployeeController;
import Controller.HolidayController;
import Model.Employee;
import Model.Poste;
import Model.Role;
import View.EmployeeView;

public class EmployeeDAOImpl implements GeneriqueDAOI<Employee>{
    private Connection connection;
    public EmployeeDAOImpl() {
        connection = DBConnection.getConnection();
    }
    @Override
    public List<Employee> afficher() {
        EmployeeController.viderLesChamps();
        String SQL = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    int id = rset.getInt("id");
                    String nom = rset.getString("nom");
                    String prenom = rset.getString("prenom");
                    double salaire = rset.getDouble("salaire");
                    String email = rset.getString("email");
                    String phone = rset.getString("phone");
                    String role = rset.getString("role");
                    String poste = rset.getString("poste");
                    int holidayBalance = rset.getInt("holidayBalance");
                    employees.add(new Employee(id, nom, prenom, salaire, email, phone, Role.valueOf(role), Poste.valueOf(poste), holidayBalance));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (employees.isEmpty()) {
            EmployeeView.AfficherFail("Aucun employé a été trouvé.");
        }
        return employees;
    }
    @Override
    public boolean ajouter(Employee employee) {
        String SQL = "INSERT INTO employee (nom, prenom, salaire, email, phone, role, poste, holidayBalance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, employee.getNom());
            stmt.setString(2, employee.getPrenom());
            stmt.setDouble(3, employee.getSalaire());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPhone());            
            stmt.setString(6, employee.getRole().name());
            stmt.setString(7, employee.getPoste().name());
            stmt.setInt(8, employee.getHolidayBalance());
            stmt.executeUpdate();
            HolidayController.setEmployeesInComboBox();
            EmployeeController.viderLesChamps();
            EmployeeView.AjouterSuccess(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public Employee findById(int EmployeeId) {
        String SQL = "SELECT * FROM employee WHERE id = ?";
        Employee employee = null;
        EmployeeController.viderLesChamps();
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, EmployeeId);
            try (ResultSet rset = stmt.executeQuery()) {                
                if(rset.next()) {
                    employee = new Employee(rset.getInt("id"), rset.getString("nom"), rset.getString("prenom"), rset.getDouble("salaire"), rset.getString("email"), rset.getString("phone"), Role.valueOf(rset.getString("role")), Poste.valueOf(rset.getString("poste")), rset.getInt("holidayBalance"));
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    @Override
    public void modifier(Employee employee, int EmployeeId) {
        String SQL = "UPDATE employee SET nom = ?, prenom = ?, salaire = ?, email = ?, phone = ?, role = ?, poste = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, employee.getNom());
            stmt.setString(2, employee.getPrenom());
            stmt.setDouble(3, employee.getSalaire());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getRole().name());
            stmt.setString(7, employee.getPoste().name());
            stmt.setInt(8, EmployeeId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                HolidayController.setEmployeesInComboBox();
                EmployeeController.viderLesChamps();
                EmployeeView.ModifierSuccess();
            } else {
                EmployeeView.ModifierFail("Aucune modification effectuée. L'ID est peut-être incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            EmployeeView.ModifierFail("Erreur lors de la mise à jour.");
        }
    }
    @Override
    public void importData(String filePath) throws IOException {
        // Assurez-vous que cette requête correspond à votre table actuelle dans la base de données
        String query = "INSERT INTO employee (nom, prenom, salaire, email, phone, role, poste) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                pstmt.setString(1, data[0].trim()); // Nom
                pstmt.setString(2, data[1].trim()); // Prénom
                pstmt.setDouble(3, Double.parseDouble(data[2].trim())); // Salaire
                pstmt.setString(4, data[3].trim()); // Email
                pstmt.setString(5, data[4].trim()); // Téléphone
                pstmt.setString(6, data[5].trim()); // Rôle
                pstmt.setString(7, data[6].trim()); // Poste
                pstmt.addBatch();
                
            }
            pstmt.executeBatch(); // Exécute toutes les requêtes préparées en batch
            System.out.println("Employees imported successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'exception pour déboguer si une erreur survient
        }
    }

    @Override
public void exportData(String fileName, List<Employee> data) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        // En-tête du fichier CSV
        writer.write("Last Name,First Name,Salary,Email,Phone,Role,Poste");
        writer.newLine();
        for (Employee employee : data) {
            // Formate chaque ligne d'employé dans le fichier
            String line = String.format("%s,%s,%.2f,%s,%s,%s,%s",
                    employee.getNom(), employee.getPrenom(),
                    employee.getSalaire(), employee.getEmail(),
                    employee.getPhone(), employee.getRole(),
                    employee.getPoste());
            writer.write(line);
            writer.newLine();
        }
        System.out.println("Employees exported successfully!");
    }
}


    @Override
    public void supprimer(int EmployeeId) {
        String SQL = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            EmployeeController.viderLesChamps();
            stmt.setInt(1, EmployeeId);
            stmt.executeUpdate();
            HolidayController.setEmployeesInComboBox();
            EmployeeView.SupprimerSuccess();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}