package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;

public class ProjectComponent extends VerticalLayout {
	private final ProjectService projectService;
	private ProjectEntity project;
	private H4 name;
	private Paragraph description;
	private Button editButton;
	private Div buttonsDiv;
	private Div textDiv;
	
	public ProjectComponent(ProjectService projectService, ProjectEntity project) {
		this.projectService = projectService;
		this.project = project;
		name = new H4(project.getName());
		description = new Paragraph(project.getDescription());
		textDiv = new Div(name, description);
		
		buttonsDiv = new Div();
		editButton = new Button("Edit");
		editButton.addClickListener(event -> {
			ProjectFormDialog projectFormDialog = new ProjectFormDialog(projectService, FormType.EDIT);
			projectFormDialog.setProject(this.project);
			projectFormDialog.open();
		});
		
		addDoubleClickListener(event -> {
			UI.getCurrent().navigate("project/" + project.getId());
		});
		
		buttonsDiv.add(editButton);
		add(textDiv, buttonsDiv);
		
		applyStyles();
	}
	
	public void applyStyles() {
		addClassName(LumoUtility.Border.ALL);
		setMaxWidth(15, Unit.REM);
		setMaxHeight(15, Unit.REM);
		textDiv.setSizeFull();
		textDiv.getStyle().set("word-wrap", "break-word");
		name.setWidthFull();
		description.setWidthFull();
		textDiv.addClassName(LumoUtility.Overflow.HIDDEN);
		
		buttonsDiv.setWidthFull();
	}
}
