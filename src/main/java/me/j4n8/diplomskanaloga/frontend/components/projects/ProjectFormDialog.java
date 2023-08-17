package me.j4n8.diplomskanaloga.frontend.components.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;

public class ProjectFormDialog extends Dialog {
	private Button deleteButton;
	private Button editButton;
	private ProjectService projectService;
	private ProjectEntity project = new ProjectEntity();
	private TextField name;
	private TextArea description;
	private Button createButton;
	private Button clearButton;
	private Div buttonsDiv;
	private Binder<ProjectEntity> binder;
	private FormType formType;
	
	public ProjectFormDialog(ProjectService projectService, FormType formType) {
		this.projectService = projectService;
		this.formType = formType;
		
		name = new TextField("Name");
		description = new TextArea("Description");
		
		createButton = new Button("Create");
		createButton.addClickListener(event -> {
			create();
		});
		
		clearButton = new Button("Clear");
		clearButton.addClickListener(event -> {
			clear();
		});
		
		editButton = new Button("Edit");
		editButton.addClickListener(event -> {
			update();
		});
		
		deleteButton = new Button("Delete");
		deleteButton.addClickListener(event -> {
			delete();
		});
		
		buttonsDiv = generateButtons();
		
		if (formType == FormType.CREATE) {
			setHeaderTitle("New project");
		} else if (formType == FormType.EDIT) {
			setHeaderTitle("Edit project");
		}
		
		add(name, description);
		getFooter().add(buttonsDiv);
		
		validation();
		applyStyles();
	}
	
	private Div generateButtons() {
		Div div = new Div();
		if (formType == FormType.CREATE) {
			div.add(createButton, clearButton);
		} else if (formType == FormType.EDIT) {
			div.add(editButton, deleteButton);
		}
		
		return div;
	}
	
	private void applyStyles() {
		setModal(true);
		
		buttonsDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Flex.AUTO, LumoUtility.Gap.SMALL);
		name.setWidthFull();
		description.setWidthFull();
	}
	
	private void validation() {
		binder = new Binder<>(ProjectEntity.class);
		binder.forField(name)
				.asRequired("Name is required")
				.bind(ProjectEntity::getName, ProjectEntity::setName);
		binder.forField(description)
				.bind(ProjectEntity::getDescription, ProjectEntity::setDescription);
	}
	
	public void setProject(ProjectEntity project){
		this.project = project;
		binder.readBean(project);
	}
	
	public void create() {
		try {
			binder.writeBean(project);
			projectService.createProject(project);
			clear();
			new Notification("Project created successfully", 5 * 1000).open();
		} catch (Exception e) {
			new Notification(e.getMessage(), 5 * 1000).open();
		}
	}
	
	public void update() {
		try {
			binder.writeBean(project);
			projectService.updateProject(project);
			this.close();
			new Notification("Project updated successfully", 5 * 1000).open();
		} catch (Exception e) {
			new Notification(e.getMessage(), 5 * 1000).open();
		}
	}
	
	public void delete() {
		try {
			projectService.deleteProject(project);
			this.close();
			new Notification("Project deleted successfully", 5 * 1000).open();
		} catch (Exception e) {
			new Notification(e.getMessage(), 5 * 1000).open();
		}
	}
	
	public void clear() {
		project = new ProjectEntity();
		binder.readBean(project);
	}
}
