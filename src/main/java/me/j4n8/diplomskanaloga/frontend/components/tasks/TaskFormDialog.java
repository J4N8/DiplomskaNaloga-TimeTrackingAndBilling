package me.j4n8.diplomskanaloga.frontend.components.tasks;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

public class TaskFormDialog extends Dialog {
	private ProjectEntity projectEntity;
	private Button deleteButton;
	private Button editButton;
	private final TaskService taskService;
	private TaskEntity task = new TaskEntity();
	private TextField title;
	private TextArea description;
	private Button createButton;
	private Button clearButton;
	private Div buttonsDiv;
	private Binder<TaskEntity> binder;
	private final FormType formType;
	
	public TaskFormDialog(TaskService taskService, FormType formType, ProjectEntity projectEntity) {
		this.taskService = taskService;
		this.formType = formType;
		this.projectEntity = projectEntity;
		
		title = new TextField("Title");
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
			create();
		});
		
		deleteButton = new Button("Delete");
		deleteButton.addClickListener(event -> {
			delete();
		});
		
		buttonsDiv = generateButtons();
		
		if (formType == FormType.CREATE) {
			setHeaderTitle("New task");
		} else if (formType == FormType.EDIT) {
			setHeaderTitle("Edit task");
		}
		add(title, description);
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
		addClassNames();
		
		buttonsDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Flex.AUTO, LumoUtility.Gap.SMALL);
		title.setWidthFull();
		description.setWidthFull();
	}
	
	private void validation() {
		binder = new Binder<>(TaskEntity.class);
		binder.forField(title)
				.asRequired("Title is required")
				.bind(TaskEntity::getTitle, TaskEntity::setTitle);
		binder.forField(description)
				.bind(TaskEntity::getDescription, TaskEntity::setDescription);
	}
	
	public void setTask(TaskEntity task) {
		this.task = task;
		binder.readBean(task);
	}
	
	public void create() {
		try {
			task.setProject(projectEntity);
			binder.writeBean(task);
			taskService.save(task);
			clear();
			new Notification("Task created successfully", 5 * 1000).open();
		} catch (Exception e) {
			new Notification(e.getMessage(), 5 * 1000).open();
		}
	}
	
	public void update() {
		try {
			binder.writeBean(task);
			taskService.save(task);
			this.close();
			new Notification("Task updated successfully", 5 * 1000).open();
		} catch (Exception e) {
			new Notification(e.getMessage(), 5 * 1000).open();
		}
	}
	
	public void delete() {
		try {
			taskService.delete(task);
			this.close();
			new Notification("Task deleted successfully", 5 * 1000).open();
		} catch (Exception e) {
			new Notification(e.getMessage(), 5 * 1000).open();
		}
	}
	
	public void clear() {
		task = new TaskEntity();
		binder.readBean(task);
	}
}
