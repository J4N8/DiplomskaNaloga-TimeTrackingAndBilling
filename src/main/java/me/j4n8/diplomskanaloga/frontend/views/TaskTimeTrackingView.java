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
import me.j4n8.diplomskanaloga.frontend.components.RefreshButton;
import me.j4n8.diplomskanaloga.frontend.components.time_tracking.TimeTrackingFormDialog;
import me.j4n8.diplomskanaloga.frontend.components.time_tracking.TimeTrackingGrid;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import me.j4n8.diplomskanaloga.time_tracking.TimeTrackingService;

@Route(value = "task", layout = MainLayout.class)
@PermitAll
public class TaskTimeTrackingView extends VerticalLayout implements HasUrlParameter<Long> {
	private final TaskService taskService;
	private final TimeTrackingService timeTrackingService;
	private TaskEntity taskEntity;
	private H2 taskTitle;
	private TimeTrackingGrid timeTrackingGrid;
	private Button startTrackingButton;
	private Div buttonsDiv;
	private Div div;
	private RefreshButton refreshButton;
	
	public TaskTimeTrackingView(TaskService taskService, TimeTrackingService timeTrackingService) {
		this.taskService = taskService;
		this.timeTrackingService = timeTrackingService;
		
		taskTitle = new H2();
		
		refreshButton = new RefreshButton();
		refreshButton.addClickListener(e -> reloadData());
		
		startTrackingButton = new Button("New tracking", e -> {
			new TimeTrackingFormDialog(timeTrackingService, taskEntity).open();
		});
		
		buttonsDiv = new Div(refreshButton, startTrackingButton);
		div = new Div(buttonsDiv, taskTitle);
		
		add(div);
	}
	
	private void reloadData() {
		timeTrackingGrid.setItems(timeTrackingService.findAllByTaskId(taskEntity));
	}
	
	private void applyStyles() {
		setSizeFull();
		div.addClassNames(LumoUtility.Display.FLEX);
		div.setWidthFull();
		taskTitle.setWidthFull();
		taskTitle.addClassName(LumoUtility.TextAlignment.CENTER);
		buttonsDiv.addClassNames(LumoUtility.Gap.SMALL, LumoUtility.Display.FLEX);
		
		timeTrackingGrid.setWidthFull();
	}
	
	@Override
	public void setParameter(BeforeEvent event, Long parameter) {
		taskEntity = taskService.findById(parameter);
		taskTitle.setText(taskEntity.getTitle());
		timeTrackingGrid = new TimeTrackingGrid(timeTrackingService, taskEntity);
		add(timeTrackingGrid);
		applyStyles();
	}
}
