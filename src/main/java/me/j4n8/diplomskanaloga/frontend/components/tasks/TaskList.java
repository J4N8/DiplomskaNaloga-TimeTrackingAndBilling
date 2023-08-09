package me.j4n8.diplomskanaloga.frontend.components.tasks;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

import java.util.List;

public class TaskList extends HorizontalLayout {
	private TaskService taskService;
	public TaskList(TaskService taskService, List<TaskEntity> tasks) {
		this.taskService = taskService;
		tasks.forEach(task -> add(new TaskComponent(taskService, task)));
	}
	
	/***
	 * Adds task to the list
	 * @param task Task to add
	 */
	public void addTask(TaskEntity task) {
		add(new TaskComponent(taskService, task));
	}
	
	/***
	 * Removes task from the list by comparing the id of the task
	 * @param task Task to remove
	 */
	public void removeTask(TaskEntity task) {
		getChildren().filter(component -> component instanceof TaskComponent).forEach(component -> {
			TaskComponent taskComponent = (TaskComponent) component;
			if (taskComponent.getTask().getId().equals(task.getId())) {
				remove(component);
			}
		});
	}
}
