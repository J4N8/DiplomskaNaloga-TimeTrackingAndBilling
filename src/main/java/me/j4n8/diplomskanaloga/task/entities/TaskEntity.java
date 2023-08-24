package me.j4n8.diplomskanaloga.task.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "completed", nullable = false)
	private boolean completed = false;
	
	// Link to user
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
	// Link to project
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private ProjectEntity project;
	
	// Link to time tracking
	@OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<TimeTrackingEntity> timeTracking;
}