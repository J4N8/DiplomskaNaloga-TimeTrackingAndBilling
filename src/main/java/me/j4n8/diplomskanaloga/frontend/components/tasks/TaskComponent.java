package me.j4n8.diplomskanaloga.frontend.components.tasks;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

public class TaskComponent extends VerticalLayout {
	private TaskService taskService;
	@Getter
	private TaskEntity task;
	private H1 title;
	private Paragraph description;
	private Checkbox completedCheckbox;
	
	
	public TaskComponent(TaskService taskService, TaskEntity task) {
		this.taskService = taskService;
		this.task = task;
		title = new H1(task.getTitle());
		description = new Paragraph(task.getDescription());
		completedCheckbox = new Checkbox("Completed", task.isCompleted());
		
		completedCheckbox.addValueChangeListener(event -> {
			this.task.setCompleted(event.getValue());
			taskService.save(this.task);
		});
		
		add(title, description, completedCheckbox);
	}
	
	public void update(TaskEntity task) {
		this.task = task;
		title.setText(task.getTitle());
		description.setText(task.getDescription());
		completedCheckbox.setValue(task.isCompleted());
	}
}
