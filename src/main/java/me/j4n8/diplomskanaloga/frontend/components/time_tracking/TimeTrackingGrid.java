package me.j4n8.diplomskanaloga.frontend.components.time_tracking;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.time_tracking.TimeTrackingService;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;

import java.time.Duration;

public class TimeTrackingGrid extends HorizontalLayout {
	private final TimeTrackingService timeTrackingService;
	private Grid<TimeTrackingEntity> grid;
	
	public TimeTrackingGrid(TimeTrackingService timeTrackingService, TaskEntity task) {
		this.timeTrackingService = timeTrackingService;
		
		grid = new Grid<>(TimeTrackingEntity.class);
		grid.setItems(timeTrackingService.findAllByTaskId(task));
		
		applyStyles();
		
		add(grid);
	}
	
	private void applyStyles() {
		setWidthFull();
		grid.setWidthFull();
		
		// Custom columns
		grid.removeAllColumns();
		grid.addColumn(timeTrackingEntity -> formatTimestampDate(timeTrackingEntity.getStartTime())).setHeader("Date");
		grid.addColumn(timeTrackingEntity -> formatTimestampTime(timeTrackingEntity.getStartTime())).setHeader("Start time");
		grid.addColumn(timeTrackingEntity -> formatTimestampDate(timeTrackingEntity.getEndTime())).setHeader("Date");
		grid.addColumn(timeTrackingEntity -> formatTimestampTime(timeTrackingEntity.getEndTime())).setHeader("End time");
		grid.addColumn(timeTrackingEntity -> formatDuration(timeTrackingEntity.getDuration())).setHeader("Duration");
	}
	
	public String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.minusHours(hours).toMinutes();
		return String.format("%02dh %02dmin", hours, minutes);
	}
	
	public String formatTimestampTime(java.sql.Timestamp timestamp) {
		if (timestamp == null) return "N/A";
		return timestamp.toString().substring(11, 16);
	}
	
	public String formatTimestampDate(java.sql.Timestamp timestamp) {
		if (timestamp == null) return "N/A";
		return timestamp.toString().substring(0, 10);
	}
}
