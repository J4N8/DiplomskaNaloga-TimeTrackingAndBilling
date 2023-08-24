package me.j4n8.diplomskanaloga.frontend.components.time_tracking;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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
		grid.addItemDoubleClickListener(event -> {
			new ConfirmDialog("Delete time tracking", "Are you sure you want to delete this time tracking?",
					"Yes", e -> {
				timeTrackingService.delete(event.getItem());
				grid.setItems(timeTrackingService.findAllByTaskId(task));
			}, "No", e -> {
			}).open();
		});
		
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
		grid.addColumn(timeTrackingEntity -> calculateCost(timeTrackingEntity)).setHeader("Cost");
	}
	
	public String calculateCost(TimeTrackingEntity timeTrackingEntity) {
		Double hourlyRate = timeTrackingEntity.getTask().getHourlyRate();
		long minutes = timeTrackingEntity.getDuration().toMinutes();
		double cost = minutes * hourlyRate / 60;
		// Format to 2 decimal places with € sign at end and 1000 separator ex. 1.234,56€
		return String.format("%,.2f€", cost);
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
