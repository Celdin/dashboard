package com.example.gitdashboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Sylvain
 *
 * Class servant � la connexion/d�connexion � la base de donn�es.
 * Elle permet �galement d'ex�cuter des requ�tes SQL.
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
	 * fermeture de la base de donn�es
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
	 * Ex�cute une requ�te pour r�cup�rer des donn�es dans la base 
	 * @param query
	 * @return le r�sultat de la requ�te
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
	 * Ex�cute une requ�te pour modifier des donn�es dans la base 
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
