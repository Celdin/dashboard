package net;

import data.DataBase;
import data.DataUsers;
import domain.User;

public class Connection {
	private User conecte;
	
	public Connection() {
		super();
		this.conecte = null;
	}
	
	public User getUser() {
		return conecte;
	}

	public boolean isConecte(){
		return conecte!=null;
	}
	
	public boolean connect(String pseudo,String password){
		DataBase bd = new DataBase();
		DataUsers du = new DataUsers();
		if(bd.verification(pseudo, password)){
			for(User u : du.getUsers()){
				if(u.getUserName().equals(pseudo)){
					conecte = u;
					return true;
				}
			}
		}
		
		return false;
	}
}
