package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Employee;
import Model.Holiday;
import Model.HolidayType;
import View.HolidayView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HolidayDAOImpl implements GeneriqueDAOI<Holiday> {
    private Connection connection;

    public HolidayDAOImpl() {
        connection = DBConnection.getConnection();
    }

    public List<Employee> afficherEmployee() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                double salaire = resultSet.getDouble("salaire");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                Model.Role role = Model.Role.valueOf(resultSet.getString("role"));
                Model.Poste poste = Model.Poste.valueOf(resultSet.getString("poste"));
                int holidayBalance = resultSet.getInt("holidayBalance");
                Employee employee = new Employee(id, nom, prenom, salaire, email, phone, role, poste, holidayBalance);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public List<Holiday> afficher() {
        List<Holiday> holidays = new ArrayList<>();
        String SQL = "SELECT id, employee_id, type, start_date, end_date FROM holiday";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int employeeId = rs.getInt("employee_id");
                String type = rs.getString("type");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");

                // Remplir l'objet Holiday avec les données récupérées
                Holiday holiday = new Holiday();
                holiday.setId(id);
                holiday.setIdEmployee(employeeId);
                holiday.setType(HolidayType.valueOf(type)); // Assure-toi que le type est un enum
                holiday.setStart(startDate.toString());
                holiday.setEnd(endDate.toString());

                holidays.add(holiday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return holidays;
    }

    @Override
    public boolean ajouter(Holiday holiday) {
        String SQL = "INSERT INTO holiday (employee_id, type, start_date, end_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, holiday.getIdEmployee());
            stmt.setString(2, holiday.getType().toString());

            // Convertir les dates en LocalDate et les insérer
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(holiday.getStart(), formatter);
            LocalDate endDate = LocalDate.parse(holiday.getEnd(), formatter);

            stmt.setDate(3, java.sql.Date.valueOf(startDate));
            stmt.setDate(4, java.sql.Date.valueOf(endDate));

            stmt.executeUpdate();

            HolidayView.success("Congé ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void modifier(Holiday holiday, int holidayId) {
        String SQL = "UPDATE holiday SET employee_id = ?, type = ?, start_date = ?, end_date = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, holiday.getIdEmployee());
            stmt.setString(2, holiday.getType().toString());

            // Convertir les dates (start et end) en java.sql.Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(holiday.getStart(), formatter);
            LocalDate endDate = LocalDate.parse(holiday.getEnd(), formatter);

            stmt.setDate(3, java.sql.Date.valueOf(startDate));  // Conversion en java.sql.Date
            stmt.setDate(4, java.sql.Date.valueOf(endDate));    // Conversion en java.sql.Date
            stmt.setInt(5, holidayId);  // Ajouter l'ID du congé à modifier

            stmt.executeUpdate();
            HolidayView.success("Congé modifié avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void modifierEmployeeBalance(Employee employee, int employeeId) {
        String SQL = "UPDATE employee SET holidayBalance = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, employee.getHolidayBalance());
            stmt.setInt(2, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int holidayId) {
        String SQL = "DELETE FROM holiday WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, holidayId);
            stmt.executeUpdate();
            HolidayView.success("Congé supprimé avec succès !");
            
            // Recharge les congés après suppression (par exemple, via un appel à afficher())
            afficher();  // Ou actualise ton affichage selon ton modèle
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Employee findById(int employeeId) {
        String SQL = "SELECT * FROM employee WHERE id = ?";
        Employee employee = null;
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDouble("salaire"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        Model.Role.valueOf(rs.getString("role")),
                        Model.Poste.valueOf(rs.getString("poste")),
                        rs.getInt("holidayBalance")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            HolidayView.fail("Erreur lors de la récupération de l'employé.");
        }
        return employee;
    }

    public Holiday FindHolidayById(int holidayId) {
        String SQL = "SELECT * FROM holiday WHERE id = ?";
        Holiday holiday = null;
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, holidayId);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    int id = rset.getInt("id");
                    int idEmployee = rset.getInt("employee_id");
                    HolidayType type = HolidayType.valueOf(rset.getString("type"));
                    String start = rset.getString("start_date");
                    String end = rset.getString("end_date");
                    holiday = new Holiday(id, idEmployee, type, start, end);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return holiday;
    }

    public List<Holiday> findByIdLoggedHoliday(int employeeId) {
        String SQL = "SELECT * FROM holiday WHERE employee_id = ?";
        List<Holiday> holidays = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    int id = rset.getInt("id");
                    int idEmployee = rset.getInt("employee_id");
                    HolidayType type = HolidayType.valueOf(rset.getString("type"));
                    String start = rset.getString("start_date");
                    String end = rset.getString("end_date");
                    holidays.add(new Holiday(id, idEmployee, type, start, end));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return holidays;
    }
}
