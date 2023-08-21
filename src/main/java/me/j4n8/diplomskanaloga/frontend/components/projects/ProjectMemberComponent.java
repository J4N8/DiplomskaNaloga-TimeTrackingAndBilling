package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;

public class ProjectMemberComponent extends Div {
	private final UserEntity member;
	private final ProjectEntity project;
	private Span username;
	private Span email;
	private Button removeButton;
	private final ProjectService projectService;
	
	public ProjectMemberComponent(ProjectService projectService, UserEntity member, ProjectEntity project) {
		this.projectService = projectService;
		this.member = member;
		this.project = project;
		
		username = new Span(member.getUsername());
		email = new Span(member.getEmail());
		removeButton = new Button("Remove");
		removeButton.addClickListener(event -> {
			removeMember();
		});
		
		add(username, email, removeButton);
	}
	
	private void removeMember() {
		projectService.removeMember(member, project);
	}
}
