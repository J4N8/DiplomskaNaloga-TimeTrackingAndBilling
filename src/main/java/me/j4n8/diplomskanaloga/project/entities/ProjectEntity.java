package me.j4n8.diplomskanaloga.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
public class ProjectEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(table = "project_members", name = "project_id", referencedColumnName = "id")
	private List<UserEntity> users;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<TaskEntity> tasks;
	
	public void addUser(UserEntity user) {
		users.add(user);
	}
	
	public void removeUser(UserEntity user) {
		users.remove(user);
	}
}