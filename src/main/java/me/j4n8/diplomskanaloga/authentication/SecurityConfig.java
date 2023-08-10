package me.j4n8.diplomskanaloga.authentication;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import me.j4n8.diplomskanaloga.frontend.views.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DatabaseUserDetailsService databaseUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
			authorizationManagerRequestMatcherRegistry
					.requestMatchers("/images/*.png").permitAll();
				});
		super.configure(http);
		setLoginView(http, LoginView.class);
		http.csrf((csrf) -> csrf.disable());
	}
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(this.databaseUserDetailsService);
		return provider;
	}
}
