package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;

import java.util.List;

public class ProjectList extends HorizontalLayout {
	private final ProjectService projectService;
	private Div projectsDiv;
	
	public ProjectList(ProjectService projectService, List<ProjectEntity> projects) {
		this.projectService = projectService;
		projectsDiv = new Div();
		projects.forEach(project -> addProject(project));
		add(projectsDiv);
		
		applyStyles();
	}
	
	/***
	 * Adds a project to the list
	 * @param project Project to be added
	 */
	public void addProject(ProjectEntity project) {
		projectsDiv.add(new ProjectComponent(projectService, project));
	}
	
	private void applyStyles() {
		setWidthFull();
		projectsDiv.setWidthFull();
		projectsDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.ROW, LumoUtility.FlexWrap.WRAP);
		projectsDiv.addClassName(LumoUtility.Gap.MEDIUM);
	}
}
