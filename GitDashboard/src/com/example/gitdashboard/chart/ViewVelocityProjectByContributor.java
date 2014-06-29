package com.example.gitdashboard.chart;

import com.example.gitdashboard.GitManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.DataSeries;

public class ViewVelocityProjectByContributor extends Chart{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le graphique "La vélocitée du projet par contributeurs"
	 * @param url
	 */
	public ViewVelocityProjectByContributor(String url){
		setCaption("La vélocitée du projet par contributeurs");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.COLUMN);
        getConfiguration().getxAxis().setType(AxisType.DATETIME);
        getConfiguration().getyAxis().getLabels().setEnabled(false);
        setWidth("500px");
        setHeight("200px");
        
        GitManager manager = new GitManager();
        for(DataSeries ds : manager.getProjectVelocityByCommiter(url))
        	getConfiguration().addSeries(ds);
	}
}
