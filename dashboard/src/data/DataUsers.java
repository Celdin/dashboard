package data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import domain.User;


public class DataUsers {
	private DataBase bd;
	private static List<User> users;
	
	public DataUsers() {
		bd = new DataBase();
		for(String s : bd.getListUser()){
			User user = new User(s);
			try {
				FileOutputStream fos = new FileOutputStream(new File(s));
				fos.close();
				DataInputStream dis = new DataInputStream(new FileInputStream(new File(s)));
				while(dis.available() != 0){
					String line = dis.readLine();
					user.addProjet(line);
				}
				dis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			users.add(user);
		}
	}
	
	public void newUser(String pseudo, String password){
		bd.newUser(pseudo, password);
		users.add(new User(pseudo));
	}
	
	public void addProjet(String pseudo,String projet){
		for(User u : users){
			if(u.getUserName().equals(pseudo)){
				try {
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(pseudo)));
					dos.writeChars(projet);
					dos.close();
					users.remove(u);
					u.addProjet(projet);
					users.add(u);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		DataUsers.users = users;
	}
}
