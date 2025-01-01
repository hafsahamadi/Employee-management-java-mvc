package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnection {
	
	
	 private static final String URL = "jdbc:postgresql://localhost:5432/JAVA12";
	    private static final String UTILISATEUR = "postgres";
	    private static final String MOT_DE_PASSE = "Amine@9182";
	    private static Connection connection = null;

	    public static Connection getConnection() {
	        if (connection == null) {
	            try {
	                Class.forName("org.postgresql.Driver");
	                connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
	            } catch (ClassNotFoundException | SQLException e) {
	                e.printStackTrace();
	                throw new RuntimeException("Erreur lors de la connexion à la base de données.");
	            }
	        }
	        return connection;
	    }
}