package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;

public class ProjectComponent extends VerticalLayout {
	private ProjectService projectService;
	@Getter
	private ProjectEntity project;
	private H2 name;
	private Paragraph description;
	private Button editButton;
	private Button selectButton;
	private Div buttonsDiv;
	
	public ProjectComponent(ProjectService projectService, ProjectEntity project) {
		this.projectService = projectService;
		this.project = project;
		name = new H2(project.getName());
		description = new Paragraph(project.getDescription());
		
		buttonsDiv = new Div();
		editButton = new Button("Edit");
		editButton.addClickListener(event -> {
			ProjectFormDialog projectFormDialog = new ProjectFormDialog(projectService, FormType.EDIT);
			projectFormDialog.setProject(this.project);
			projectFormDialog.open();
		});
		
		selectButton = new Button("Select");
		selectButton.addClickListener(event -> {
			UI.getCurrent().navigate("project/" + project.getId());
		});
		
		buttonsDiv.add(selectButton, editButton);
		add(name, description, buttonsDiv);
		
		applyStyles();
	}
	
	public void applyStyles() {
		addClassName(LumoUtility.Border.ALL);
	}
}
