package me.j4n8.diplomskanaloga.task;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	private final SecurityService securityService;
	
	public TaskService(TaskRepository taskRepository, SecurityService securityService) {
		this.taskRepository = taskRepository;
		this.securityService = securityService;
	}
	
	/***
	 * Creates new task and sets user to authenticated user
	 * @param task Task to create
	 * @return Created task
	 */
	public TaskEntity createTask(TaskEntity task) {
		UserEntity authenticatedUser = securityService.getAuthenticatedUser();
		task.setUser(authenticatedUser);
		return taskRepository.save(task);
	}
	
	/***
	 * Saves task to database and sets user to authenticated user
	 * @param task Task to save
	 * @return Saved task
	 */
	public TaskEntity save(TaskEntity task) {
		if (securityService.hasNoTaskPermission(task)) {
			throw new IllegalArgumentException("You do not have permission to edit this task");
		}
		return taskRepository.save(task);
	}
	
	/***
	 * Finds all tasks assigned to authenticated user
	 * @return List of tasks
	 */
	public List<TaskEntity> findAllAssignedToAuthUser() {
		return taskRepository.findByUser_Id(securityService.getAuthenticatedUser().getId());
	}
	
	/***
	 * Deletes task from database
	 * @param task Task to delete
	 */
	public void delete(TaskEntity task) {
		if (securityService.hasNoTaskPermission(task)) {
			throw new IllegalArgumentException("You do not have permission to delete this task");
		}
		taskRepository.delete(task);
	}
	
	public List<TaskEntity> findAllByProject(ProjectEntity projectEntity) {
		if (securityService.hasNoProjectPermission(projectEntity)) {
			throw new IllegalArgumentException("You do not have permission to view this project");
		}
		return taskRepository.findByProject_Id(projectEntity.getId());
	}
	
	public TaskEntity findById(TaskEntity taskEntity) {
		return findById(taskEntity.getId());
	}
	
	public TaskEntity findById(Long id) {
		TaskEntity task = taskRepository.findById(id).orElse(null);
		if (securityService.hasNoTaskPermission(task)) {
			throw new IllegalArgumentException("You do not have permission to view this task");
		}
		return task;
	}
}
