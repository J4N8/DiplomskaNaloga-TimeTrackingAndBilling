package me.j4n8.diplomskanaloga.authentication;

import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockSecurityService {
	@Bean
	public SecurityService securityService() {
		return new SecurityService(null) {
			@Override
			public UserEntity getAuthenticatedUser() {
				return new UserEntity(1L, "testUser", "testPassword", "testEmail", null, null);
			}
		};
	}
}