package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.MainLayout;
import me.j4n8.diplomskanaloga.frontend.components.tasks.TaskFormDialog;
import me.j4n8.diplomskanaloga.frontend.components.tasks.TaskList;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.task.TaskService;

@Route(value = "tasks", layout = MainLayout.class)
@PermitAll
public class AllTasksView extends VerticalLayout {
	private TaskService taskService;
	private H1 title;
	private TaskList taskList;
	private Button newTaskButton;
	private Div div;
	
	public AllTasksView(TaskService taskService){
		this.taskService = taskService;
		addClassName("all-tasks-view");
		setSizeFull();
		
		title = new H1("All tasks");
		taskList = new TaskList(this.taskService, this.taskService.findAll());
		newTaskButton = new Button("New task", e -> {
			TaskFormDialog taskFormDialog = new TaskFormDialog(taskService, FormType.CREATE);
			taskFormDialog.open();
		});
		
		div = new Div(newTaskButton, title);
		
		add(div, taskList);
		applyStyles();
	}
	
	public void applyStyles(){
		div.addClassNames(LumoUtility.Display.FLEX);
		div.setWidthFull();
		title.setWidthFull();
		title.addClassName(LumoUtility.TextAlignment.CENTER);
	}
}
