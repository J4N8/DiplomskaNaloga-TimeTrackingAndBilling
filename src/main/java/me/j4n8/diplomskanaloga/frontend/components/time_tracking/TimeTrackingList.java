package me.j4n8.diplomskanaloga.frontend.components.time_tracking;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.time_tracking.TimeTrackingService;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;

import java.util.List;

public class TimeTrackingList extends HorizontalLayout {
	private final TimeTrackingService timeTrackingService;
	private Div timeTrackingDiv;
	
	public TimeTrackingList(TimeTrackingService timeTrackingService, List<TimeTrackingEntity> timeTrackings) {
		this.timeTrackingService = timeTrackingService;
		
		timeTrackingDiv = new Div();
		timeTrackings.forEach(timeTracking -> {
			addTimeTracking(timeTracking);
		});
		
		add(timeTrackingDiv);
		
		applyStyles();
	}
	
	private void applyStyles() {
		setWidthFull();
		timeTrackingDiv.setWidthFull();
		timeTrackingDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.ROW, LumoUtility.FlexWrap.WRAP);
		timeTrackingDiv.addClassName(LumoUtility.Gap.MEDIUM);
	}
	
	public void addTimeTracking(TimeTrackingEntity timeTracking) {
		timeTrackingDiv.add(new TimeTrackingComponent(timeTrackingService, timeTracking));
	}
	
	public void setTimeTrackings(List<TimeTrackingEntity> list) {
		timeTrackingDiv.removeAll();
		list.forEach(timeTracking -> addTimeTracking(timeTracking));
	}
}
