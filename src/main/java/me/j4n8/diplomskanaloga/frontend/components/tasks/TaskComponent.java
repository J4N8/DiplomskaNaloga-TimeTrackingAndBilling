package me.j4n8.diplomskanaloga.frontend.components.tasks;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import me.j4n8.diplomskanaloga.frontend.enums.FormType;
import me.j4n8.diplomskanaloga.task.TaskService;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;

public class TaskComponent extends VerticalLayout {
	private final TaskService taskService;
	@Getter
	private TaskEntity task;
	private H4 title;
	private Paragraph description;
	private Checkbox completedCheckbox;
	private Div buttonsDiv;
	private Button editButton;
	private Div textDiv;
	private Span assigneeSpan;
	private Div priceDiv;
	private Span hourlyRateSpan;
	private Span totalCostSpan;
	
	public TaskComponent(TaskService taskService, TaskEntity task) {
		this.taskService = taskService;
		this.task = task;
		title = new H4(task.getTitle());
		description = new Paragraph(task.getDescription());
		assigneeSpan = new Span(LumoIcon.USER.create(), new Text(task.getUser().getUsername()));
		textDiv = new Div(title, description);
		
		hourlyRateSpan = new Span(LumoIcon.CLOCK.create(), new Text(String.format("%,.2f€", task.getHourlyRate()) + "/h"));
		hourlyRateSpan.setTitle("Hourly rate");
		totalCostSpan = new Span(VaadinIcon.MONEY.create(), new Text(String.format("%,.2f€", task.getTotalCost())));
		totalCostSpan.setTitle("Total cost");
		priceDiv = new Div(hourlyRateSpan, totalCostSpan);
		

		completedCheckbox = new Checkbox("Completed", task.isCompleted());
		completedCheckbox.addValueChangeListener(event -> {
			this.task.setCompleted(event.getValue());
			taskService.save(this.task);
			applyStyles();
		});
		
		buttonsDiv = new Div();
		editButton = new Button("Edit");
		editButton.addClickListener(event -> {
			TaskFormDialog taskFormDialog = new TaskFormDialog(taskService, FormType.EDIT, this.task.getProject());
			taskFormDialog.setTask(this.task);
			taskFormDialog.open();
		});
		
		buttonsDiv.add(editButton, completedCheckbox);
		add(textDiv, assigneeSpan, priceDiv, buttonsDiv);
		
		addDoubleClickListener(event -> {
			UI.getCurrent().navigate("task/" + task.getId());
		});
		
		applyStyles();
	}
	
	private void applyStyles() {
		setClassName(null);
		addClassName(LumoUtility.Border.ALL);
		
		// Color the task component based on the completion status
		addClassName(task.isCompleted() ? LumoUtility.Background.SUCCESS_10 : LumoUtility.Background.ERROR_10);
		
		setMaxWidth(15, Unit.REM);
		setMaxHeight(15, Unit.REM);
		textDiv.setSizeFull();
		textDiv.getStyle().set("word-wrap", "break-word");
		title.setWidthFull();
		description.setWidthFull();
		textDiv.addClassName(LumoUtility.Overflow.HIDDEN);
		
		buttonsDiv.setWidthFull();
		
		assigneeSpan.setWidthFull();
		
		priceDiv.setWidthFull();
		priceDiv.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL);
	}
}
