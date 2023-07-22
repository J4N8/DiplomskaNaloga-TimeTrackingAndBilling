package me.j4n8.diplomskanaloga.time_tracking;

import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackingRepository extends JpaRepository<TimeTrackingEntity, Long> {
}