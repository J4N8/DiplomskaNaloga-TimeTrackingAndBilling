package me.j4n8.diplomskanaloga.authentication;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import me.j4n8.diplomskanaloga.frontend.views.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MyUserDetailsService userDetailsService;
	
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
	
//	@Bean
//	public UserDetailsService userDetailsService() {
////		UserDetails user = User.builder()
////				.username("user")
////				// password = password with this hash, don't tell anybody :-)
////				.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
////				.roles("USER")
////				.build();
//		return new JdbcUserDetailsManager();
//	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
