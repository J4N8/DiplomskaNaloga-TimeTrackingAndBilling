package me.j4n8.diplomskanaloga.authentication;

import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockSecurityService {
	@Bean
	public SecurityService securityService() {
		return new SecurityService(null, null) {
			@Override
			public UserEntity getAuthenticatedUser() {
				return new UserEntity(1L, "testUser", "testPassword", "testEmail", null, null);
			}
			
			@Override
			public boolean hasNoProjectPermission(me.j4n8.diplomskanaloga.project.entities.ProjectEntity accessedProject) {
				return false;
			}
			
			@Override
			public boolean hasNoTaskPermission(me.j4n8.diplomskanaloga.task.entities.TaskEntity accessedTask) {
				return false;
			}
			
			@Override
			public boolean hasNoTimeTrackingPermission(me.j4n8.diplomskanaloga.task.entities.TaskEntity accessedTask) {
				return false;
			}
		};
	}
}