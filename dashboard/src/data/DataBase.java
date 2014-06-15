package data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

	private final static String fileName = "data.txt";
	
	private static List<User> users;
	
	public DataBase() {
		super();
		users = new ArrayList<User>();
		try {
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			fos.close();
			DataInputStream dis = new DataInputStream(new FileInputStream(new File(fileName)));
			while(dis.available() != 0){
				String line = dis.readLine();
				User user = new User(line.split(" ")[0],line.split(" ")[1] );
				users.add(user);
			}
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public boolean verification(String pseudo,String password){
		User user = new User(pseudo, password);
		for(User u : users)
			if(u.equals(user))
				return true;
		return false;
	}

	public void newUser(String pseudo,String password){
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(fileName)));
			dos.writeChars(pseudo + " " + password);
			dos.close();
			users.add(new User(pseudo, password));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String> getListUser(){
		List<String> list = new ArrayList<String>();
		
		for (User u : users){
			list.add(u.pseudo);
		}
		
		
		return list;
	}
	
	private class User{
		private String pseudo;
		private String mdp;
		public User(String pseudo, String mdp) {
			super();
			this.pseudo = pseudo;
			this.mdp = mdp;
		}
		
	}
}
