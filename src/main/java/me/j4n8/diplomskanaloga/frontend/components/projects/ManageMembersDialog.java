package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;

import java.util.List;

public class ManageMembersDialog extends Dialog {
	private final ProjectService projectService;
	private List<UserEntity> members;
	private ProjectEntity project;
	
	private EmailField email;
	private Div buttonsDiv;
	private Button inviteButton;
	private Div membersDiv;
	
	public ManageMembersDialog(ProjectService projectService, ProjectEntity project) {
		this.projectService = projectService;
		this.project = project;
		
		email = new EmailField("Email");
		email.setClearButtonVisible(true);
		email.setRequired(true);
		
		buttonsDiv = generateButtons();
		
		add(email, buttonsDiv);
		
		this.members = projectService.getMembers(project);
		membersDiv = new Div();
		
		members.forEach(member -> {
			membersDiv.add(new ProjectMemberComponent(projectService, member, project));
		});
		add(membersDiv);
	}
	
	private Div generateButtons() {
		inviteButton = new Button("Invite");
		inviteButton.addClickListener(event -> {
			invite();
		});
		
		return new Div(inviteButton);
	}
	
	private void invite() {
		if (email.isInvalid() || email.isEmpty()) {
			email.setErrorMessage("Please enter a valid email address");
			email.setInvalid(true);
			return;
		}
		
		boolean result = projectService.inviteMember(project, email.getValue());
		if (result) {
			new Notification("Invitation sent!", 5 * 1000).open();
		} else {
			email.setErrorMessage("There is no user with that email address");
			email.setInvalid(true);
		}
	}
}
