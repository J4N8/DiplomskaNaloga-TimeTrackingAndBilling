package me.j4n8.diplomskanaloga.task;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
	private TaskRepository taskRepository;
	private SecurityService securityService;
	
	public TaskService(TaskRepository taskRepository, SecurityService securityService) {
		this.taskRepository = taskRepository;
		this.securityService = securityService;
	}
	
	/***
	 * Creates new task and sets user to authenticated user
	 * @param title Title of task
	 * @param description Description of task
	 * @return Created task
	 */
	public TaskEntity createTask(String title, String description) {
		UserEntity authenticatedUser = securityService.getAuthenticatedUser();
		TaskEntity newTask = new TaskEntity(null, title, description, false, authenticatedUser);
		return taskRepository.save(newTask);
	}
	
	/***
	 * Saves task to database and sets user to authenticated user
	 * @param task Task to save
	 * @return Saved task
	 */
	public TaskEntity save(TaskEntity task) {
		UserEntity user = securityService.getAuthenticatedUser();
		task.setUser(user);
		return taskRepository.save(task);
	}
	
	/***
	 * Finds all tasks for authenticated user
	 * @return List of tasks
	 */
	public List<TaskEntity> findAll() {
		return taskRepository.findByUser_Id(securityService.getAuthenticatedUser().getId());
	}
	
	/***
	 * Deletes task from database
	 * @param task Task to delete
	 */
	public void delete(TaskEntity task) {
		taskRepository.delete(task);
	}
}
