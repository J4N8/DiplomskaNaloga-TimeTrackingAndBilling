package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.MainLayout;
import me.j4n8.diplomskanaloga.frontend.components.RefreshButton;
import me.j4n8.diplomskanaloga.frontend.components.tasks.TaskList;
import me.j4n8.diplomskanaloga.task.TaskService;

@Route(value = "tasks", layout = MainLayout.class)
@PermitAll
public class AllTasksView extends VerticalLayout {
	private final TaskService taskService;
	private H1 title;
	private TaskList taskList;
	private RefreshButton refreshButton;
	private Div div;
	
	public AllTasksView(TaskService taskService){
		this.taskService = taskService;
		addClassName("all-tasks-view");
		setSizeFull();
		
		title = new H1("All tasks");
		refreshButton = new RefreshButton();
		refreshButton.addClickListener(e -> reloadData());
		taskList = new TaskList(this.taskService, this.taskService.findAllAssignedToAuthUser());
		
		div = new Div(refreshButton, title);
		
		add(div, taskList);
		applyStyles();
	}
	
	private void reloadData() {
		taskList.setTasks(this.taskService.findAllAssignedToAuthUser());
	}
	
	public void applyStyles(){
		title.setWidthFull();
		title.addClassName(LumoUtility.TextAlignment.CENTER);
		div.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL);
		div.setWidthFull();
	}
}
