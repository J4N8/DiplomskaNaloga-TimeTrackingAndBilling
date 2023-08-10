package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.tasks.TaskForm;
import me.j4n8.diplomskanaloga.task.TaskService;

@Route(value = "tasks/new")
@PermitAll
public class NewTaskView extends VerticalLayout {
	private TaskService taskService;
	private H1 title;
	private TaskForm taskForm;
	
	public NewTaskView(TaskService taskService) {
		this.taskService = taskService;
		addClassName("new-task-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		title = new H1("New task");
		taskForm = new TaskForm(this.taskService);
		
		add(title, taskForm);
	}
}
