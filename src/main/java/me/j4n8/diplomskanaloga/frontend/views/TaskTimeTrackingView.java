package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import me.j4n8.diplomskanaloga.frontend.components.MainLayout;
import me.j4n8.diplomskanaloga.frontend.components.time_tracking.TimeTrackingList;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.time_tracking.TimeTrackingService;
import me.j4n8.diplomskanaloga.time_tracking.entities.TimeTrackingEntity;

import java.util.List;

@Route(value = "task", layout = MainLayout.class)
@PermitAll
public class TaskTimeTrackingView extends VerticalLayout implements HasUrlParameter<Long> {
	private final TaskService taskService;
	private final TimeTrackingService timeTrackingService;
	private TaskEntity taskEntity;
	private H2 taskTitle;
	private TimeTrackingList timeTrackingList;
	private Button startTrackingButton;
	private Div buttonsDiv;
	private Div div;
	
	public TaskTimeTrackingView(TaskService taskService, TimeTrackingService timeTrackingService) {
		this.taskService = taskService;
		this.timeTrackingService = timeTrackingService;
		
		taskTitle = new H2();
		timeTrackingList = new TimeTrackingList(timeTrackingService, List.of());
		
		startTrackingButton = new Button("Start new tracking", e -> {
			TimeTrackingEntity timeTrackingEntity = new TimeTrackingEntity();
			timeTrackingEntity.setTask(taskEntity);
			timeTrackingEntity = timeTrackingService.create(timeTrackingEntity);
			
			timeTrackingList.addTimeTracking(timeTrackingEntity);
		});
		
		buttonsDiv = new Div(startTrackingButton);
		div = new Div(buttonsDiv, taskTitle);
		
		add(div, timeTrackingList);
		
		applyStyles();
	}
	
	private void applyStyles() {
		setSizeFull();
		div.addClassNames(LumoUtility.Display.FLEX);
		div.setWidthFull();
		taskTitle.setWidthFull();
		taskTitle.addClassName(LumoUtility.TextAlignment.CENTER);
		
		buttonsDiv.addClassNames(LumoUtility.Gap.SMALL, LumoUtility.Display.FLEX);
	}
	
	@Override
	public void setParameter(BeforeEvent event, Long parameter) {
		taskEntity = taskService.findById(parameter);
		taskTitle.setText(taskEntity.getTitle());
		timeTrackingList.setTimeTrackings(taskEntity.getTimeTracking());
	}
}
