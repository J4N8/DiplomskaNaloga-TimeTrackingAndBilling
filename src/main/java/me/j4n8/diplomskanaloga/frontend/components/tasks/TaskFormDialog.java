package me.j4n8.diplomskanaloga.frontend.components.tasks;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

@SpringComponent
public class TaskFormDialog extends Dialog {
	private TaskService taskService;
	private TaskEntity task = new TaskEntity();
	private TextField title;
	private TextArea description;
	private Button createButton;
	private Button clearButton;
	private Binder<TaskEntity> binder;
	
	public TaskFormDialog(TaskService taskService) {
		this.taskService = taskService;
		
		title = new TextField("Title");
		description = new TextArea("Description");
		
		createButton = new Button("Create");
		createButton.addClickListener(event -> {
			save();
		});
		
		clearButton = new Button("Clear");
		clearButton.addClickListener(event -> {
			clear();
		});
		
		Div buttonsDiv = new Div(clearButton, createButton);
		
		add(title, description, buttonsDiv);
		
		validation();
		applyStyles();
	}
	
	private void applyStyles() {
		setModal(true);
		addClassNames();
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
		binder.readBean(task);
	}
	
	public void save() {
		try {
			binder.writeBean(task);
			taskService.save(task);
			clear();
			new Notification("Task created successfully", 5 * 1000).open();
		} catch (Exception e) {
			new Notification(e.getMessage(), 5 * 1000).open();
		}
	}
	
	public void delete() {
		taskService.delete(task);
	}
	
	public void clear() {
		task = new TaskEntity();
		binder.readBean(task);
	}
}
