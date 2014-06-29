package com.example.gitdashboard.chart;

import com.example.gitdashboard.GitManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;

public class ViewIssue extends Chart{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Le graphique "Nombre d'issues ouvertes"
	 * @param url
	 */
	public ViewIssue(String url){
		setCaption("Nombre d'issues ouvertes");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.LINE);
        getConfiguration().getxAxis().setType(AxisType.DATETIME);
        getConfiguration().getLegend().setEnabled(false);
        setWidth("500px");
        setHeight("200px");
        
        GitManager manager = new GitManager();
        getConfiguration().setSeries(manager.getIssuesStats(url));
	}
}
