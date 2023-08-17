package me.j4n8.diplomskanaloga.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	// Link to tasks
	@OneToMany(mappedBy = "user")
	private java.util.List<me.j4n8.diplomskanaloga.task.entities.TaskEntity> tasks;
	
	// Link to projects
	@OneToMany(mappedBy = "user")
	private java.util.List<me.j4n8.diplomskanaloga.project.entities.ProjectEntity> projects;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("User"));
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}