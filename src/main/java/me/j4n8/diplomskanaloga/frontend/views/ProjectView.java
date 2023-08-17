package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.MainLayout;
import me.j4n8.diplomskanaloga.frontend.components.tasks.TaskFormDialog;
import me.j4n8.diplomskanaloga.frontend.components.tasks.TaskList;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.task.TaskService;

import java.util.List;

@Route(value = "project", layout = MainLayout.class)
@PermitAll
public class ProjectView extends VerticalLayout implements HasUrlParameter<Long> {
	private final TaskService taskService;
	private final ProjectService projectService;
	private ProjectEntity projectEntity;
	private H1 name;
	private TaskList taskList;
	private Button newTaskButton;
	private Div div;
	
	public ProjectView(TaskService taskService, ProjectService projectService) {
		this.taskService = taskService;
		this.projectService = projectService;
		addClassName("project-view");
		setSizeFull();
		
		name = new H1("");
		taskList = new TaskList(this.taskService, List.of());
		newTaskButton = new Button("New task", e -> {
			TaskFormDialog taskFormDialog = new TaskFormDialog(taskService, FormType.CREATE, projectEntity);
			taskFormDialog.open();
		});
		
		div = new Div(newTaskButton, name);
		
		add(div, taskList);
		applyStyles();
	}
	
	public void applyStyles() {
		div.addClassNames(LumoUtility.Display.FLEX);
		div.setWidthFull();
		name.setWidthFull();
		name.addClassName(LumoUtility.TextAlignment.CENTER);
	}
	
	@Override
	public void setParameter(BeforeEvent event, Long parameter) {
		projectEntity = projectService.findById(parameter);
		name.setText(projectEntity.getName());
		taskList.setTasks(taskService.findAllByProject(projectEntity));
	}
}
