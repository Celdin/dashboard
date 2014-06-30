package com.example.gitdashboard;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

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
		User user;
		@Override
		public int compareTo(Issue arg0) {
			return this.created_at.compareTo(arg0.created_at);
		}
		private class User{
			String login;
		}
	}
	
	/**
	 * @author Sylvain
	 *
	 * Interface git servant à la récupération de toutes les "Issues" d'un projet.
	 */
	interface AllIssues {
		@GET("/repos/{owner}/{repo}/issues?state=all&per_page=100")
		List<Issue> issues(
			@Path("owner")	String owner,
			@Path("repo") String repo,
			@Query("page") Integer page
				);
		
	}
	
	/**
	 * @author Sylvain
	 *
	 * Interface git servant à la récupération de toutes les "Issues" ouverte d'un projet.
	 */
	interface IssuesOpen {
		@GET("/repos/{owner}/{repo}/issues?state=open&per_page=100")
		List<Issue> issues(
			@Path("owner")	String owner,
			@Path("repo") String repo,
			@Query("page") Integer page
				);
		
	}
	
	/**
	 * @author Sylvain
	 *
	 * Interface git servant à la récupération de toutes les "Issues" fermée d'un projet.
	 */
	interface IssuesClose {
		@GET("/repos/{owner}/{repo}/issues?state=close&per_page=100")
		List<Issue> issues(
			@Path("owner")	String owner,
			@Path("repo") String repo,
			@Query("page") Integer page
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
		List<Issue> issues = new ArrayList<Issue>();
		int i = 1;
		do{
			issues.addAll(gitHub.issues(owner, repo,i));
			i++;
		}while(issues.size() != 0 && issues.size()%100 == 0);
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
		List<Issue> issues = new ArrayList<Issue>();
		int i = 1;
		do{
			issues.addAll(gitHub.issues(owner, repo,i));
			i++;
		}while(issues.size() != 0 && issues.size()%100 == 0);
		return issues;
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
		List<Issue> issues = new ArrayList<Issue>();
		int i=1;
		do{
			issues.addAll(gitHub.issues(owner, repo,i));
			i++;
		}while(issues.size() != 0 && issues.size()%100  == 0);
		return issues;
	}
	
	/**
	 * Sert à la récupération de toutes les nom des contributeurs d'un projet.
	 * @param url
	 * @return la liste des contributeurs
	 */
	private List<String> getContributor(String url){
		List<String> names = new ArrayList<String>();
		List<Issue> issues = getIssuesClose(url);
		for(Issue issue : issues){
			if(!names.contains(issue.user.login)){
				names.add(issue.user.login);
			}
		}
		return names;
	}
	
	/**
	 * @param url
	 * @return La liste des Issues avec la date de creation
	 */
	public List<DataSeries> getIssuesStats(List<String> urls){
		List<DataSeries> resultats = new ArrayList<DataSeries>();
		for(String url : urls){
			List<Issue> issues = getIssues(url);
			DataSeries resultat = new DataSeries(url.substring(url.lastIndexOf("/") + 1));
			Collections.sort(issues);
			for (int i = 0; i < issues.size(); i++){
				int nb_issue = i + 1;
				for(Issue is : issues){
					if(is.closed_at != null &&  is.closed_at.compareTo(issues.get(i).created_at) < 0){
						nb_issue--;
					}
				}
				resultat.add(new DataSeriesItem(issues.get(i).created_at, nb_issue));
			}
			resultats.add(resultat);
		}
		return resultats;
	}
	
	/**
	 * @param url
	 * @return Le nombre d'issue fermée et ouverte
	 */
	public DataSeries getIssueByStates(List<String> urls){
		DataSeries resultat = new DataSeries("Issues");
		int open = 0;
		int close = 0;
		for(String url : urls){
			List<Issue> issues = getIssuesOpen(url);
			open += issues.size();
			issues = getIssuesClose(url);
			close += issues.size();
		}
		resultat.add(new DataSeriesItem("Open", open));
		resultat.add(new DataSeriesItem("Close", close));
		return resultat;
	}
	
	/**
	 * @param url
	 * @return le nombre de commit en fonction du temps (commit/jours).
	 */
	public List<DataSeries> getProjectVelocity(List<String> urls){
		List<DataSeries> resultats = new ArrayList<DataSeries>();
		for(String url : urls){
			DataSeries resultat = new DataSeries(url.substring(url.lastIndexOf("/") + 1));
			List<Issue> commits = getIssuesClose(url);
			for(Issue cd1 : commits){
				Date date1 = new Date(cd1.closed_at.getYear(), cd1.closed_at.getMonth(), cd1.closed_at.getDate());
					int count = 1;
					for(Issue cd2 : commits){
						Date date2 = new Date(cd2.closed_at.getYear(), cd2.closed_at.getMonth(), cd2.closed_at.getDate());					
						if (date1.equals(date2)){
							count++;
						}
				}
				resultat.add(new DataSeriesItem(date1, count));
			}
			resultats.add(resultat);
		}
		return resultats;
	}
	
	/**
	 * @param url
	 * @return une liste de issue en fonction du contributeur pour chaque projet
	 */
	public List<DataSeries> getProjectVelocityByCommiter(ArrayList<String> urls){
		List<DataSeries> resultats = new ArrayList<DataSeries>();
		List<String> users = new ArrayList<String>();
		for(String url : urls){
			for(String user : getContributor(url)){
				if(!users.contains(user))
					users.add(user);
			}
		}
		for(String user : users){
			DataSeries series = new DataSeries(user);
			for(String url : urls){
				int cpt = 0;
				for(Issue issue : getIssuesClose(url)){
					if(issue.user.login.equals(user))
						cpt++;
				}
				series.add(new DataSeriesItem(url.substring(url.lastIndexOf("/") + 1), cpt));
			}
			resultats.add(series);
		}
		return resultats;
	}
}
