package me.j4n8.diplomskanaloga.time_tracking;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeTrackingService {
	private final TimeTrackingRepository timeTrackingRepository;
	private final SecurityService securityService;
	
	public TimeTrackingService(TimeTrackingRepository timeTrackingRepository, SecurityService securityService) {
		this.timeTrackingRepository = timeTrackingRepository;
		this.securityService = securityService;
	}
	
	public void delete(TimeTrackingEntity timeTracking) {
		if (securityService.hasNoTaskPermission(timeTracking.getTask())) {
			throw new IllegalArgumentException("You do not have permission to delete this time tracking");
		}
		timeTrackingRepository.delete(timeTracking);
	}
	
	public TimeTrackingEntity create(TimeTrackingEntity timeTrackingEntity) {
		if (securityService.hasNoTaskPermission(timeTrackingEntity.getTask())) {
			throw new IllegalArgumentException("You do not have permission to create this time tracking");
		}
		return timeTrackingRepository.save(timeTrackingEntity);
	}
	
	public List<TimeTrackingEntity> findAllByTaskId(TaskEntity task) {
		if (securityService.hasNoTaskPermission(task)) {
			throw new IllegalArgumentException("You do not have permission to view this time tracking");
		}
		return timeTrackingRepository.findByTask_Id(task.getId());
	}
}
