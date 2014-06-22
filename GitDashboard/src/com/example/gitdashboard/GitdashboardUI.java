package com.example.gitdashboard;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.thirdparty.javascript.jscomp.parsing.parser.trees.SetAccessorTree;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dashboard")
@Title("Dashboard")
public class GitdashboardUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CssLayout content = new CssLayout();
	private CssLayout root = new CssLayout();
	private CssLayout repo = new CssLayout();
	private boolean estConecte = false;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GitdashboardUI.class)
	public static class Servlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	@Override
	protected void init(VaadinRequest request) {
		getSession().setConverterFactory(new DefaultConverterFactory());
		setContent(root);
		
		root.setSizeFull();

		buildMainView();
	}

	private void buildMainView() {
		root.addComponent(new VerticalLayout() {
			private static final long serialVersionUID = 1L;

			{
				addStyleName("main-view");
				//Header
				addComponent(new HorizontalLayout() {
					private static final long serialVersionUID = 1L;

					{
						addStyleName("header");
						setWidth("100%");
						setMargin(true);
						Label logo = new Label("Dashboard", ContentMode.HTML);
						logo.setSizeUndefined();
						addComponent(logo);
						setComponentAlignment(logo, Alignment.MIDDLE_LEFT);
						HorizontalLayout loger = new HorizontalLayout();
						addComponent(loger);
						viewLogin(loger);
						setComponentAlignment(loger, Alignment.MIDDLE_RIGHT);
					}
				});
				//body
				addComponent(new HorizontalLayout(){
					{
						addStyleName("body");
						//sidebare
						addComponent(new VerticalLayout(){
							{
								addStyleName("sidebar");
								setMargin(true);
								setHeight("10000px");
								TextField newRepo = new TextField("Nouveaux Repository");
								addComponent(newRepo);
								addComponent(repo);
								setExpandRatio(repo, 1);
							}
						});
						// Content
		                addComponent(content);
		                content.setSizeFull();
		                content.addStyleName("view-content");
		                setExpandRatio(content, 1);
					}
				});
			}
		});
	}
	private void viewLogin(final HorizontalLayout loger){
		loger.removeAllComponents();
		if (estConecte) {
			final Button deconecter = new Button("Deconecter");
			deconecter.addStyleName("default");
			loger.addComponent(deconecter);
			deconecter.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					estConecte = false;
					viewLogin(loger);
				}
			});
		} else {
			final TextField username = new TextField(
					"Login");
			loger.addComponent(username);
			final PasswordField password = new PasswordField(
					"Password");
			loger.addComponent(password);
			final Button signin = new Button("Sign In");
			signin.addStyleName("default");
			loger.addComponent(signin);
			signin.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					if (username.getValue() != null
	                        && username.getValue().equals("toto")
	                        && password.getValue() != null
	                        && password.getValue().equals("tata")){
						estConecte = true;
						viewLogin(loger);
					}
				}
			});
			loger.setComponentAlignment(signin, Alignment.MIDDLE_RIGHT);
		}
	}

}