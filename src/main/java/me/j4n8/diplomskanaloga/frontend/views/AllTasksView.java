package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.tasks.TaskList;
import me.j4n8.diplomskanaloga.task.TaskService;

@Route(value = "tasks")
@PermitAll
public class AllTasksView extends VerticalLayout {
	private TaskService taskService;
	
	private H1 title;
	private TaskList taskList;
	
	public AllTasksView(TaskService taskService){
		this.taskService = taskService;
		addClassName("all-tasks-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		title = new H1("All tasks");
		taskList = new TaskList(this.taskService, this.taskService.findAll());
		
		add(title, taskList);
	}
}
