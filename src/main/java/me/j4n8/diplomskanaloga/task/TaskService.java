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
	
	public TaskEntity createTask(String title, String description) {
		UserEntity authenticatedUser = securityService.getAuthenticatedUser();
		TaskEntity newTask = new TaskEntity(null, title, description, false, authenticatedUser);
		return taskRepository.save(newTask);
	}
	
	public TaskEntity save(TaskEntity task) {
		return taskRepository.save(task);
	}
	
	public List<TaskEntity> findAll() {
		return taskRepository.findByUser_Id(securityService.getAuthenticatedUser().getId());
	}
}
