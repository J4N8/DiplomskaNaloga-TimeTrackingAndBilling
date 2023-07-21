package me.j4n8.diplomskanaloga.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@Entity
@Table(name = "time_trackings")
public class TimeTracking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "start_time", nullable = false)
	private java.sql.Timestamp startTime;
	
	@Column(name = "end_time", nullable = false)
	private java.sql.Timestamp endTime;
	
	@Column(name = "duration", nullable = false)
	private Duration duration;
}