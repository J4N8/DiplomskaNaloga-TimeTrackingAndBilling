package me.j4n8.diplomskanaloga.frontend.components.tasks;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

public class TaskComponent extends VerticalLayout {
	private TaskService taskService;
	@Getter
	private TaskEntity task;
	private H2 title;
	private Paragraph description;
	private Checkbox completedCheckbox;
	private Div buttonsDiv;
	private Button editButton;
	
	public TaskComponent(TaskService taskService, TaskEntity task) {
		this.taskService = taskService;
		this.task = task;
		title = new H2(task.getTitle());
		description = new Paragraph(task.getDescription());
		completedCheckbox = new Checkbox("Completed", task.isCompleted());
		
		completedCheckbox.addValueChangeListener(event -> {
			this.task.setCompleted(event.getValue());
			taskService.save(this.task);
			applyStyles();
		});
		
		buttonsDiv = new Div();
		editButton = new Button("Edit");
		editButton.addClickListener(event -> {
			TaskFormDialog taskFormDialog = new TaskFormDialog(taskService, FormType.EDIT);
			taskFormDialog.setTask(this.task);
			taskFormDialog.open();
		});
		
		buttonsDiv.add(editButton);
		add(title, description, completedCheckbox, buttonsDiv);
		
		applyStyles();
	}
	
	private void applyStyles() {
		setClassName(null);
		addClassName(LumoUtility.Border.ALL);
		
		// Color the task component based on the completion status
		addClassName(task.isCompleted() ? LumoUtility.Background.SUCCESS_10 : LumoUtility.Background.ERROR_10);
	}
	
	public void update(TaskEntity task) {
		this.task = task;
		title.setText(task.getTitle());
		description.setText(task.getDescription());
		completedCheckbox.setValue(task.isCompleted());
	}
}
