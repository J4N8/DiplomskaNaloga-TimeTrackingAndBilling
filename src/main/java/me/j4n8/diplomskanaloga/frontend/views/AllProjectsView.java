package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.MainLayout;
import me.j4n8.diplomskanaloga.frontend.components.RefreshButton;
import me.j4n8.diplomskanaloga.frontend.components.projects.ProjectFormDialog;
import me.j4n8.diplomskanaloga.frontend.components.projects.ProjectList;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.project.ProjectService;

@Route(value = "projects", layout = MainLayout.class)
@PermitAll
public class AllProjectsView extends VerticalLayout {
	private final ProjectService projectService;
	private H1 title;
	private ProjectList projectList;
	private Button newProjectButton;
	private Div div;
	private RefreshButton refreshButton;
	
	public AllProjectsView(ProjectService projectService) {
		this.projectService = projectService;
		addClassName("projects-view");
		setSizeFull();
		
		title = new H1("Projects");
		refreshButton = new RefreshButton();
		refreshButton.addClickListener(e -> reloadData());
		projectList = new ProjectList(this.projectService, this.projectService.findAll());
		newProjectButton = new Button("New project", e -> {
			ProjectFormDialog projectFormDialog = new ProjectFormDialog(projectService, FormType.CREATE);
			projectFormDialog.open();
		});
		
		div = new Div(refreshButton, newProjectButton, title);
		
		add(div, projectList);
		applyStyles();
	}
	
	private void reloadData() {
		projectList.setProjects(this.projectService.findAll());
	}
	
	public void applyStyles(){
		div.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL);
		div.setWidthFull();
		title.setWidthFull();
		title.addClassName(LumoUtility.TextAlignment.CENTER);
	}
}
