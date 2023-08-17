package me.j4n8.diplomskanaloga.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;

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
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
}