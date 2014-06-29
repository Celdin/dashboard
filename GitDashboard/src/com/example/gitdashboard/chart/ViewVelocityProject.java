package com.example.gitdashboard.chart;

import com.example.gitdashboard.GitManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;

public class ViewVelocityProject extends Chart{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * le graphique "La vélocitée du projet"
	 * @param url
	 */
	public ViewVelocityProject(String url){
		setCaption("La vélocitée du projet");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.COLUMN);
        getConfiguration().getxAxis().setType(AxisType.DATETIME);
        getConfiguration().getLegend().setEnabled(false);
        setWidth("500px");
        setHeight("200px");
        
        GitManager manager = new GitManager();
        getConfiguration().setSeries(manager.getProjectVelocity(url));
	}
}
