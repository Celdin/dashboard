package com.example.gitdashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;

/**
 * @author Sylvain
 *
 * Classe servant à la récupération des données github.
 */
public class GitManager{

	/**
	 * @author Sylvain
	 * Classe pour récupérer les "Issues" d'un projet.
	 */
	private class Issue implements Comparable<Issue>{
		Date created_at;
		Date closed_at;
		@Override
		public int compareTo(Issue arg0) {
			return this.created_at.compareTo(arg0.created_at);
		}
	}
	
	/**
	 * @author Sylvain
	 * Classe pour récupérer les "Commit" d'un projet.
	 *
	 */
	private class CommitData{
		private Commit commit;
		public class Commit{
			private Committer committer;
			public class Committer{
				String name;
				Date date;
			}
		}
	}
	
	/**
	 * @author Sylvain
	 *
	 * Interface git servant à la récupération de toutes les "Issues" d'un projet.
	 */
	interface AllIssues {
		@GET("/repos/{owner}/{repo}/issues?state=all")
		List<Issue> issues(
			@Path("owner")	String owner,
			@Path("repo") String repo
				);
		
	}
	
	/**
	 * @author Sylvain
	 *
	 * Interface git servant à la récupération de toutes les "Issues" ouverte d'un projet.
	 */
	interface IssuesOpen {
		@GET("/repos/{owner}/{repo}/issues?state=open")
		List<Issue> issues(
			@Path("owner")	String owner,
			@Path("repo") String repo
				);
		
	}
	
	/**
	 * @author Sylvain
	 *
	 * Interface git servant à la récupération de toutes les "Issues" fermée d'un projet.
	 */
	interface IssuesClose {
		@GET("/repos/{owner}/{repo}/issues?state=close")
		List<Issue> issues(
			@Path("owner")	String owner,
			@Path("repo") String repo
				);
		
	}
	
	/**
	 * @author Sylvain
	 *
	 * Interface git servant à la récupération de toutes les "Commit" d'un projet.
	 */
	interface AllCommits {
		@GET("/repos/{owner}/{repo}/commits")
		List<CommitData> commits(
			@Path("owner")	String owner,
			@Path("repo") String repo
				);
		
	}
	
	/**
	 * Sert à la récupération de toutes les "Issues" d'un projet.
	 * @param url
	 * @return la liste des "Issues"
	 */
	private List<Issue> getIssues(String url){
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
		AllIssues gitHub = adapter.create(AllIssues.class);
		String owner = url.substring("https://github.com".length() + 1,url.lastIndexOf("/"));
		String repo =  url.substring(url.lastIndexOf("/") + 1);
		List<Issue> issues = gitHub.issues(owner, repo);
		return issues;
	}
	
	/**
	 * Sert à la récupération de toutes les "Issues" ouverte d'un projet.
	 * @param url
	 * @return la liste des "Issues" ouverte
	 */
	private List<Issue> getIssuesOpen(String url){
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
		IssuesOpen gitHub = adapter.create(IssuesOpen.class);
		String owner = url.substring("https://github.com".length() + 1,url.lastIndexOf("/"));
		String repo =  url.substring(url.lastIndexOf("/") + 1);
		List<Issue> issues = gitHub.issues(owner, repo);
		return issues;
	}
	
	/**
	 * Sert à la récupération de toutes les "Commits" d'un projet.
	 * @param url
	 * @return la liste des "Commit"
	 */
	private List<CommitData> getCommitData(String url){
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
		AllCommits gitHub = adapter.create(AllCommits.class);
		String owner = url.substring("https://github.com".length() + 1,url.lastIndexOf("/"));
		String repo =  url.substring(url.lastIndexOf("/") + 1);
		List<CommitData> commits = gitHub.commits(owner, repo);
		return commits;
	}
	
	/**
	 * Sert à la récupération de toutes les "Issues" fermée d'un projet.
	 * @param url
	 * @return la liste des "Issues" fermée
	 */
	private List<Issue> getIssuesClose(String url){
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();
		IssuesClose gitHub = adapter.create(IssuesClose.class);
		String owner = url.substring("https://github.com".length() + 1,url.lastIndexOf("/"));
		String repo =  url.substring(url.lastIndexOf("/") + 1);
		List<Issue> issues = gitHub.issues(owner, repo);
		return issues;
	}
	
	/**
	 * Sert à la récupération de toutes les nom des contributeurs d'un projet.
	 * @param url
	 * @return la liste des contributeurs
	 */
	private List<String> getCommiter(String url){
		List<String> names = new ArrayList<String>();
		List<CommitData> commits = getCommitData(url);
		for(CommitData cd : commits){
			if(!names.contains(cd.commit.committer.name)){
				names.add(cd.commit.committer.name);
			}
		}
		return names;
	}
	
	/**
	 * @param url
	 * @return La liste des Issues avec la date de creation
	 */
	public DataSeries getIssuesStats(String url){
		List<Issue> issues = getIssues(url);
		DataSeries resultat = new DataSeries("Nombre d'issues ouvertes");
		Collections.sort(issues);
		for (int i = 0; i < issues.size(); i++){
			int nb_issue = i + 1;
			/*
			 * soustrait les issue fermée. 
			for(Issue is : issues){
				if(is.closed_at != null &&  is.closed_at.compareTo(issues.get(i).created_at) < 0){
					nb_issue--;
				}
			}*/
			resultat.add(new DataSeriesItem(issues.get(i).created_at, nb_issue));
		}
		return resultat;
	}
	
	/**
	 * @param url
	 * @return Le nombre d'issue fermée et ouverte
	 */
	public DataSeries getIssueByStates(String url){
		DataSeries resultat = new DataSeries("Issues");
		List<Issue> issues = getIssuesOpen(url);
		resultat.add(new DataSeriesItem("Open", issues.size()));
		issues = getIssuesClose(url);
		resultat.add(new DataSeriesItem("Close", issues.size()));
		return resultat;
	}
	
	/**
	 * @param url
	 * @return le nombre de commit en fonction du temps (commit/jours).
	 */
	public DataSeries getProjectVelocity(String url){
		DataSeries resultat = new DataSeries("Nombres de commits");
		List<CommitData> commits = getCommitData(url);
		for(CommitData cd1 : commits){
			Date date1 = new Date(cd1.commit.committer.date.getYear(), cd1.commit.committer.date.getMonth(), cd1.commit.committer.date.getDate());
				int count = 1;
				for(CommitData cd2 : commits){
					Date date2 = new Date(cd2.commit.committer.date.getYear(), cd2.commit.committer.date.getMonth(), cd2.commit.committer.date.getDate());					
					if (date1.equals(date2)){
						count++;
					}
			}
			resultat.add(new DataSeriesItem(date1, count));
		}
		return resultat;
	}
	
	/**
	 * @param url
	 * @return une liste de commit en fonction du temps (commit/jours) pour chaque contributeurs
	 */
	public List<DataSeries> getProjectVelocityByCommiter(String url){
		List<DataSeries> resultats = new ArrayList<DataSeries>();
		List<CommitData> commits = getCommitData(url);
		List<String> names = getCommiter(url);
		for(String name : names){
			DataSeries resultat = new DataSeries(name);
			for(CommitData cd1 : commits){
				if(cd1.commit.committer.name.equals(name)){
					Date date1 = new Date(cd1.commit.committer.date.getYear(), cd1.commit.committer.date.getMonth(), cd1.commit.committer.date.getDate());
						int count = 1;
						for(CommitData cd2 : commits){
							Date date2 = new Date(cd2.commit.committer.date.getYear(), cd2.commit.committer.date.getMonth(), cd2.commit.committer.date.getDate());					
							if (date1.equals(date2) && cd2.commit.committer.name.equals(name)){
								count++;
							}
					}
					resultat.add(new DataSeriesItem(date1, count));
				}
			}
			resultats.add(resultat);
		}
		return resultats;
	}
}
