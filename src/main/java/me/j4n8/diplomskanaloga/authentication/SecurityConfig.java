package me.j4n8.diplomskanaloga.authentication;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		http
				.authorizeHttpRequests((authorizeHttpRequests) ->
						authorizeHttpRequests
								.anyRequest().permitAll()
				);
		http.csrf((csrf) -> csrf.disable());
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
				.username("user")
				// password = password with this hash, don't tell anybody :-)
				.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
}
