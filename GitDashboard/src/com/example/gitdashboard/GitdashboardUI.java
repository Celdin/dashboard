package com.example.gitdashboard;

import javax.servlet.annotation.WebServlet;

import com.example.gitdashboard.chart.ViewIssue;
import com.example.gitdashboard.chart.ViewIssueByStates;
import com.example.gitdashboard.chart.ViewVelocityProject;
import com.example.gitdashboard.chart.ViewVelocityProjectByContributor;
import com.example.gitdashboard.domain.User;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
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
	private UserManager user;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GitdashboardUI.class,widgetset="com.example.gitdashboard.MyWidgetSet")
	public static class Servlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	@Override
	protected void init(VaadinRequest request) {
		getSession().setConverterFactory(new DefaultConverterFactory());
		user = new UserManager();
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
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					{
						addStyleName("body");
						//sidebare
						addComponent(new VerticalLayout(){
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							{
								addStyleName("sidebar");
								setMargin(true);
								setHeight("10000px");
								addComponent(new HorizontalLayout(){
									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;

									{
										final TextField newRepo = new TextField("Nouveaux Repository");
										addComponent(newRepo);
										Button add = new NativeButton("+");
										addComponent(add);
										setComponentAlignment(add, Alignment.BOTTOM_RIGHT);
										add.addClickListener(new Button.ClickListener() {
											
											/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

											@Override
											public void buttonClick(ClickEvent event) {
												UserManager.saveRepository(newRepo.getValue());
												newRepo.setValue("");
												refrechRepo();
											}
										});
									}
								});
								addComponent(repo);
								setExpandRatio(repo, 1);
							}
						});
						// Content
		                addComponent(content);
		                content.setSizeFull();
		                content.addStyleName("view-content");
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
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					repo.removeAllComponents();
					UserManager.user = new User();
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

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					if (UserManager.connect(username.getValue(), password.getValue())){
						refrechRepo();
						estConecte = true;
						viewLogin(loger);
					}
				}
			});
			loger.setComponentAlignment(signin, Alignment.MIDDLE_RIGHT);
		}
	}

	private void refrechRepo() {
		repo.removeAllComponents();
		repo.addComponent(new VerticalLayout(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				for(final String url : UserManager.user.getRepos()){
					addComponent(new HorizontalLayout(){
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						{
							Button del = new NativeButton("-");
							addComponent(del);
							del.addClickListener(new Button.ClickListener() {
								
								/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								@Override
								public void buttonClick(ClickEvent event) {
									UserManager.deleteRepository(url);
									refrechRepo();
								}
							});
							Button repository = new NativeButton(url.substring(url.lastIndexOf("/")+1));
							addComponent(repository);
							repository.setWidth("100%");;
							repository.addClickListener(new Button.ClickListener() {
								
								/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								@Override
								public void buttonClick(ClickEvent event) {
									updateMainContent(url);
								}
							});
						}
					});

				}
			}
		});
	}
	
	private void updateMainContent(final String url){
		content.removeAllComponents();
		content.addComponent(new HorizontalLayout(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setSizeFull();
		        setMargin(new MarginInfo(true, true, false, true));
		        setSpacing(true);
                addStyleName("view-content");
				addComponent(new VerticalLayout(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					{
				        setSpacing(true);
						addComponent(createPanel(new ViewIssue(url)));
				        addComponent(createPanel(new ViewIssueByStates(url)));
					}
				});
				addComponent(new VerticalLayout(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					{
				        setSpacing(true);
						addComponent(createPanel(new ViewVelocityProject(url)));
				        addComponent(createPanel(new ViewVelocityProjectByContributor(url)));
					}
				});
			}
		});
	}
	
	private CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();

        panel.addComponent(content);
        return panel;
    }

}