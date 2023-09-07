package me.j4n8.diplomskanaloga.authentication;

import com.vaadin.flow.spring.security.AuthenticationContext;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
	private final AuthenticationContext authenticationContext;
	
	public SecurityService(AuthenticationContext authenticationContext) {
		this.authenticationContext = authenticationContext;
	}
	
	public UserEntity getAuthenticatedUser() {
		return authenticationContext.getAuthenticatedUser(UserEntity.class).get();
	}
	
	public void logout() {
		authenticationContext.logout();
	}
	
	public boolean hasNoProjectPermission(ProjectEntity accessedProject) {
		return getAuthenticatedUser().getProjects().stream().noneMatch(project -> project.getId().equals(accessedProject.getId()));
	}
	
	public boolean hasNoTaskPermission(TaskEntity accessedTask) {
		return getAuthenticatedUser().getTasks().stream().noneMatch(task -> task.getId().equals(accessedTask.getId()));
	}
}
