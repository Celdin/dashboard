package com.example.gitdashboard.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sylvain
 * Class de définition d'un utilisateur.
 */
public class User {
	private String pseudo;
	private List<String> repos;
	
	public User(String pseudo, List<String> repos) {
		super();
		this.setPseudo(pseudo);
		this.setRepos(repos);
	}

	public User() {
		super();
		this.setPseudo(null);
		this.setRepos(new ArrayList<String>());
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public List<String> getRepos() {
		return repos;
	}

	public void setRepos(List<String> repos) {
		this.repos = repos;
	}
}
