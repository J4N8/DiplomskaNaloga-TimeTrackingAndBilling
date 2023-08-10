package me.j4n8.diplomskanaloga.authentication;

import com.vaadin.flow.spring.security.AuthenticationContext;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
	private AuthenticationContext authenticationContext;
	
	public SecurityService(AuthenticationContext authenticationContext) {
		this.authenticationContext = authenticationContext;
	}
	
	public UserEntity getAuthenticatedUser() {
		return authenticationContext.getAuthenticatedUser(UserEntity.class).get();
	}
	
	public void logout() {
		authenticationContext.logout();
	}
}
