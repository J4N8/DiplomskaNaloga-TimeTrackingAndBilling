package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;

public class ProjectMemberComponent extends Div {
	private final UserEntity member;
	private final ProjectEntity project;
	private Span details;
	private Button removeButton;
	private final ProjectService projectService;
	
	public ProjectMemberComponent(ProjectService projectService, UserEntity member, ProjectEntity project) {
		this.projectService = projectService;
		this.member = member;
		this.project = project;
		details = new Span(member.getUsername() + " | " + member.getEmail());
		removeButton = new Button("Remove");
		removeButton.addClickListener(event -> {
			removeMember();
		});
		
		add(details, removeButton);
		
		applyStyles();
	}
	
	private void applyStyles() {
		addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL);
		details.setWidthFull();
	}
	
	private void removeMember() {
		new ConfirmDialog("Are you sure you want to remove this member?", "This action cannot be undone",
				"Remove", e -> {
			projectService.removeMember(member, project);
		}, "Cancel", e -> {
		}).open();
	}
}
