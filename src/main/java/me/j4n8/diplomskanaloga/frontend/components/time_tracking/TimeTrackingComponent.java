package me.j4n8.diplomskanaloga.frontend.components.time_tracking;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.time_tracking.TimeTrackingService;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;

public class TimeTrackingComponent extends Div {
	private final TimeTrackingService timeTrackingService;
	private TimeTrackingEntity timeTracking;
	private Div timeDiv;
	private Span durationSpan;
	private Span startTimeSpan;
	private Span endTimeSpan;
	private Div buttonDiv;
	private Button deleteButton;
	
	public TimeTrackingComponent(TimeTrackingService timeTrackingService, TimeTrackingEntity timeTracking) {
		this.timeTrackingService = timeTrackingService;
		this.timeTracking = timeTracking;
		
		//TODO: Fix time format to be more readable (HH:MM:SS)
		durationSpan = new Span(timeTracking.getDuration().toString());
		durationSpan.setTitle("Duration");
		startTimeSpan = new Span(timeTracking.getStartTime().toString());
		startTimeSpan.setTitle("Start time");
		if (timeTracking.getEndTime() != null) {
			endTimeSpan = new Span(timeTracking.getEndTime().toString());
		} else {
			endTimeSpan = new Span("N/A");
		}
		endTimeSpan.setTitle("End time");
		
		timeDiv = new Div(durationSpan, startTimeSpan, endTimeSpan);
		buttonDiv = generateButtons();
		
		add(timeDiv, buttonDiv);
		
		applyStyles();
	}
	
	private void applyStyles() {
		buttonDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.ROW, LumoUtility.Gap.SMALL);
		buttonDiv.setWidthFull();
		timeDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.FlexWrap.WRAP, LumoUtility.Gap.SMALL);
		timeDiv.setWidthFull();
		addClassNames(LumoUtility.Border.ALL);
	}
	
	private Div generateButtons() {
		deleteButton = new Button("Delete");
		deleteButton.addClickListener(event -> {
			timeTrackingService.delete(timeTracking);
		});
		
		return new Div(deleteButton);
	}
}
