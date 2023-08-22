package me.j4n8.diplomskanaloga.time_tracking;

import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TimeTrackingService {
	private final TimeTrackingRepository timeTrackingRepository;
	
	public TimeTrackingService(TimeTrackingRepository timeTrackingRepository) {
		this.timeTrackingRepository = timeTrackingRepository;
	}
	
	public void delete(TimeTrackingEntity timeTracking) {
		timeTrackingRepository.delete(timeTracking);
	}
	
	public TimeTrackingEntity create(TimeTrackingEntity timeTrackingEntity) {
		timeTrackingEntity.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
		timeTrackingEntity.setDuration(Duration.ZERO);
		return timeTrackingRepository.save(timeTrackingEntity);
	}
}
