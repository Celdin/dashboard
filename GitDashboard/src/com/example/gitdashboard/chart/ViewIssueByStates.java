package com.example.gitdashboard.chart;

import com.example.gitdashboard.GitManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;

public class ViewIssueByStates extends Chart{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Le graphique "La répartition des issues par état"
	 * @param url
	 */
	public ViewIssueByStates(String url){
		setCaption("La répartition des issues par état");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.PIE);
        getConfiguration().getxAxis().getLabels().setEnabled(false);
        getConfiguration().getxAxis().setTickWidth(0);
        setWidth("500px");
        setHeight("200px");
        
        GitManager manager = new GitManager();
        getConfiguration().setSeries(manager.getIssueByStates(url));
	}
}
