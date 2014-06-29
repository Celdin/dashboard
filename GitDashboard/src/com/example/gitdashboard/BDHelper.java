package com.example.gitdashboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Sylvain
 *
 * Class servant à la connexion/déconnexion à la base de données.
 * Elle permet également d'exécuter des requêtes SQL.
 */
public class BDHelper {
	private Connection connection;
	private Statement statement;
	
	/**
	 * Constructeur permettant la connexion
	 */
	public BDHelper() {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dashdoard?"
		              + "user=root&password=root");
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * fermeture de la base de données
	 */
	public void close(){
		try {
			connection.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Exécute une requête pour récupérer des données dans la base 
	 * @param query
	 * @return le résultat de la requête
	 */
	public ResultSet query(String query){
		ResultSet resultat = null;
		try {
			resultat = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultat;
	}

	/**
	 * Exécute une requête pour modifier des données dans la base 
	 * @param query
	 */
	public void queryUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
