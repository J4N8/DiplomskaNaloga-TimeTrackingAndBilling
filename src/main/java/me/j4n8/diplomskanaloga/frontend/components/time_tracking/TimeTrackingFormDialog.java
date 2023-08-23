package me.j4n8.diplomskanaloga.frontend.components.time_tracking;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.time_tracking.TimeTrackingService;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeTrackingFormDialog extends Dialog {
	private final TimeTrackingService timeTrackingService;
	private TimeTrackingEntity timeTrackingEntity;
	private TaskEntity taskEntity;
	private Div buttonsDiv;
	private Button createButton;
	private DateTimePicker startTimeField;
	private DateTimePicker endTimeField;
	private Binder<TimeTrackingEntity> binder;
	
	public TimeTrackingFormDialog(TimeTrackingService timeTrackingService, TaskEntity task) {
		this.timeTrackingService = timeTrackingService;
		this.taskEntity = task;
		this.timeTrackingEntity = new TimeTrackingEntity();
		
		startTimeField = new DateTimePicker("Start time");
		startTimeField.setMax(LocalDateTime.now().minusMinutes(1));
		startTimeField.setStep(Duration.ofMinutes(30));
		endTimeField = new DateTimePicker("End time");
		endTimeField.setStep(Duration.ofMinutes(30));
		endTimeField.setMax(LocalDateTime.now());
		
		buttonsDiv = generateButtons();
		add(startTimeField, endTimeField, buttonsDiv);
		
		validation();
		applyStyles();
	}
	
	private void applyStyles() {
		setModal(true);
		setHeaderTitle("New time tracking");
		addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN);
		startTimeField.setWidthFull();
		endTimeField.setWidthFull();
	}
	
	private Div generateButtons() {
		createButton = new Button("Create", e -> {
			create();
		});
		return new Div(createButton);
	}
	
	private void create() {
		timeTrackingEntity.setDuration(Duration.between(timeTrackingEntity.getStartTime().toLocalDateTime(), timeTrackingEntity.getEndTime().toLocalDateTime()));
		timeTrackingEntity.setTask(taskEntity);
		
		if (binder.validate().isOk()) {
			timeTrackingService.create(timeTrackingEntity);
			close();
		} else {
			new Notification("Invalid input", 5 * 1000).open();
		}
	}
	
	private void validation() {
		binder = new Binder<>(TimeTrackingEntity.class);
		binder.forField(startTimeField)
				.asRequired("Start time is required")
				.withConverter(this::convertToTimestamp, this::convertToLocalDateTime)
				.bind(TimeTrackingEntity::getStartTime, TimeTrackingEntity::setStartTime);
		binder.forField(endTimeField)
				.asRequired("End time is required")
				.withValidator(endTime -> endTime.isAfter(startTimeField.getValue()), "End time must be after start time")
				.withConverter(this::convertToTimestamp, this::convertToLocalDateTime)
				.bind(TimeTrackingEntity::getEndTime, TimeTrackingEntity::setEndTime);
		binder.setBean(timeTrackingEntity);
	}
	
	private Timestamp convertToTimestamp(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		return Timestamp.valueOf(localDateTime);
	}
	
	private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return timestamp.toLocalDateTime();
	}
}
