package com.example.gitdashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.gitdashboard.domain.User;

public class UserManager {

	static BDHelper bd;
	static User user;
	public UserManager() {
		user = new User();
	}
	
	public static boolean connect(String login,String mdp){

		bd = new BDHelper();
		ResultSet result = bd.query("SELECT * FROM User WHERE login = '" + login + "' AND mdp = '" + mdp + "'");
		
		try {
			if(result.next()){
				List <String> rep = new ArrayList<String>();
				rep = getRepository(result.getInt(result.findColumn("id")));
				user = new User(login, rep);
				System.out.println(user.getRepos());
				bd.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bd.close();
		return false;
	}
	
	private static List<String> getRepository(int user_id){
		List<String> rep = new ArrayList<String>();
		ResultSet result = bd.query("SELECT * FROM repository WHERE id_user=" + user_id);
		try {
			result.first();
			do{
				rep.add(result.getString(result.findColumn("repo_url")));
				
			}while(result.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rep;
	}
	
	public static void saveRepository(String url){
		bd = new BDHelper();
		user.getRepos().add(url);
		if(user.getPseudo() != null){
			ResultSet result = bd.query("SELECT * FROM user WHERE login='" + user.getPseudo() + "'");
			try {
				result.first();
				bd.queryUpdate("INSERT INTO repository (id_user,repo_url) VALUES (" + result.getInt(result.findColumn("id"))+ ",'" + url + "')");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void deleteRepository(String url){
		bd = new BDHelper();
		user.getRepos().remove(url);
		if(user.getPseudo() != null){
			ResultSet result = bd.query("SELECT * FROM user WHERE login='" + user.getPseudo() + "'");
			try {
				result.first();
				result = bd.query("SELECT * FROM repository where id_user=" + result.getInt(result.findColumn("id")) + " AND repo_url='" + url + "'");
				result.first();
				bd.queryUpdate("DELETE FROM repository WHERE id=" + result.getInt(result.findColumn("id")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
