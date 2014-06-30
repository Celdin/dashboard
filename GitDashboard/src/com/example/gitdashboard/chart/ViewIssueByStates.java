package com.example.gitdashboard.chart;

import java.util.ArrayList;

import com.example.gitdashboard.GitManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;

public class ViewIssueByStates extends Chart{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Le graphique "La r�partition des issues par �tat"
	 * @param url
	 */
	public ViewIssueByStates(ArrayList<String> urls){
		setCaption("La r�partition des issues par �tat");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.PIE);
        getConfiguration().getxAxis().getLabels().setEnabled(false);
        getConfiguration().getxAxis().setTickWidth(0);
        setWidth("500px");
        setHeight("200px");
        
        GitManager manager = new GitManager();
        getConfiguration().setSeries(manager.getIssueByStates(urls));
	}
}
