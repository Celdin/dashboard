package com.example.gitdashboard.chart;

import java.util.ArrayList;

import com.example.gitdashboard.GitManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.DataSeries;

public class ViewIssue extends Chart{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Le graphique "Nombre d'issues ouvertes"
	 * @param url
	 */
	public ViewIssue(ArrayList<String> urls){
		setCaption("Nombre d'issues ouvertes");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.LINE);
        getConfiguration().getxAxis().setType(AxisType.DATETIME);
        setWidth("500px");
        setHeight("200px");
        
        GitManager manager = new GitManager();
        for(DataSeries ds : manager.getIssuesStats(urls))
        	getConfiguration().addSeries(ds);
	}
}
