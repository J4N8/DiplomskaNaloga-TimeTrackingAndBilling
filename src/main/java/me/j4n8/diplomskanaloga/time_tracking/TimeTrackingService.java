package me.j4n8.diplomskanaloga.time_tracking;

import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
		return timeTrackingRepository.save(timeTrackingEntity);
	}
	
	public List<TimeTrackingEntity> findAllByTaskId(TaskEntity task) {
		return timeTrackingRepository.findByTask_Id(task.getId());
	}
}
