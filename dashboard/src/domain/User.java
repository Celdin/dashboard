package domain;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String userName;
	private List<String> projets;
	
	public User(String userName) {
		super();
		this.userName = userName;
		projets = new ArrayList<String>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getProjets() {
		return projets;
	}

	public void addProjet(String projet) {
		projets.add(projet);
	}
}
