package me.j4n8.diplomskanaloga.authentication;

import com.vaadin.flow.spring.security.AuthenticationContext;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.task.TaskRepository;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
	private final AuthenticationContext authenticationContext;
	private final TaskRepository taskRepository;
	
	public SecurityService(AuthenticationContext authenticationContext, TaskRepository taskRepository) {
		this.authenticationContext = authenticationContext;
		this.taskRepository = taskRepository;
	}
	
	public UserEntity getAuthenticatedUser() {
		return authenticationContext.getAuthenticatedUser(UserEntity.class).get();
	}
	
	public void logout() {
		authenticationContext.logout();
	}
	
	public boolean hasNoProjectPermission(ProjectEntity accessedProject) {
		UserEntity user = getAuthenticatedUser();
		return accessedProject.getUsers().stream().noneMatch(member -> member.getId().equals(user.getId()));
	}
	
	public boolean hasNoTaskPermission(TaskEntity accessedTask) {
		TaskEntity task = taskRepository.findById(accessedTask.getId()).orElse(null);
		return hasNoProjectPermission(task.getProject());
	}
	
	public boolean hasNoTimeTrackingPermission(TaskEntity accessedTask) {
		List<TaskEntity> assigned = taskRepository.findByUser_Id(getAuthenticatedUser().getId());
		return assigned.stream().noneMatch(task -> task.getId().equals(accessedTask.getId()));
	}
}
