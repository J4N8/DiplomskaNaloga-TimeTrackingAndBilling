package me.j4n8.diplomskanaloga.frontend.components.tasks;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

import java.util.List;

public class TaskList extends HorizontalLayout {
	private final TaskService taskService;
	private Div tasksDiv;
	public TaskList(TaskService taskService, List<TaskEntity> tasks) {
		this.taskService = taskService;
		tasksDiv = new Div();
		tasks.forEach(task -> addTask(task));
		
		add(tasksDiv);
		
		applyStyles();
	}
	
	/***
	 * Adds task to the list
	 * @param task Task to add
	 */
	public void addTask(TaskEntity task) {
		tasksDiv.add(new TaskComponent(taskService, task));
	}
	
	public void setTasks(List<TaskEntity> tasks) {
		tasksDiv.removeAll();
		tasks.forEach(task -> addTask(task));
	}
	
	private void applyStyles() {
		setWidthFull();
		tasksDiv.setWidthFull();
		tasksDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.ROW, LumoUtility.FlexWrap.WRAP);
		tasksDiv.addClassName(LumoUtility.Gap.MEDIUM);
	}
}
