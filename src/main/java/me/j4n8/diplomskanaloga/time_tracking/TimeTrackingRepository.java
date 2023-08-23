package me.j4n8.diplomskanaloga.time_tracking;

import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTrackingRepository extends JpaRepository<TimeTrackingEntity, Long> {
	List<TimeTrackingEntity> findByTask_Id(Long id);
	
}