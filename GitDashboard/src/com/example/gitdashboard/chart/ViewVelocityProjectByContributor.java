package com.example.gitdashboard.chart;

import java.util.ArrayList;

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
	 * @param urls
	 */
	public ViewVelocityProjectByContributor(ArrayList<String> urls){
		setCaption("La vélocitée du projet par contributeurs");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.COLUMN);
        getConfiguration().getxAxis().setType(AxisType.CATEGORY);
        setWidth("500px");
        setHeight("200px");
        
        GitManager manager = new GitManager();
        for(DataSeries series : manager.getProjectVelocityByCommiter(urls))
        	getConfiguration().addSeries(series);
	}
}
