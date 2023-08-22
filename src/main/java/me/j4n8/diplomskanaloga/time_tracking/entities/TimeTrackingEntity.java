package me.j4n8.diplomskanaloga.time_tracking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "time_trackings")
public class TimeTrackingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "start_time", nullable = false)
	private java.sql.Timestamp startTime;
	
	@Column(name = "end_time", nullable = true)
	private java.sql.Timestamp endTime;
	
	@Column(name = "duration", nullable = false)
	private Duration duration;
	
	// Link to task
	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private TaskEntity task;
}