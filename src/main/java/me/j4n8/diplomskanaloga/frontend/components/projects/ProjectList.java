package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;

import java.util.List;

public class ProjectList extends HorizontalLayout {
	private final ProjectService projectService;
	
	public ProjectList(ProjectService projectService, List<ProjectEntity> projects) {
		this.projectService = projectService;
		projects.forEach(project -> addProject(project));
	}
	
	/***
	 * Adds a project to the list
	 * @param project Project to be added
	 */
	public void addProject(ProjectEntity project) {
		add(new ProjectComponent(projectService, project));
	}
	
	/***
	 * Removes a project from the list
	 * @param project Project to be removed
	 */
	public void removeTask(ProjectEntity project) {
		getChildren().filter(component -> component instanceof ProjectComponent).forEach(component -> {
			ProjectComponent projectComponent = (ProjectComponent) component;
			if (projectComponent.getProject().getId().equals(project.getId())) {
				remove(component);
			}
		});
	}
}
