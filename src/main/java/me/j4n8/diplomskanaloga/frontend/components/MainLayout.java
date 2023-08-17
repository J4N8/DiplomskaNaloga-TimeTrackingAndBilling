package me.j4n8.diplomskanaloga.frontend.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.frontend.views.AllProjectsView;
import me.j4n8.diplomskanaloga.frontend.views.AllTasksView;
import me.j4n8.diplomskanaloga.frontend.views.HomepageView;
import me.j4n8.diplomskanaloga.task.TaskService;

public class MainLayout extends AppLayout {
	private final SecurityService securityService;
	private final TaskService taskService;
	
	public MainLayout(SecurityService securityService, TaskService taskService) {
		this.securityService = securityService;
		this.taskService = taskService;
		createHeader();
		createDrawer();
	}
	
	private void createHeader() {
		RouterLink logo = new RouterLink("Time tracking and billing app", HomepageView.class);
		logo.addClassNames(
				LumoUtility.FontSize.LARGE,
				LumoUtility.Margin.MEDIUM);
		
		Button logoutButton = new Button("Log out", e -> securityService.logout());
		
		var header = new HorizontalLayout(new DrawerToggle(), logo, logoutButton);
		
		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.expand(logo);
		header.setWidthFull();
		header.addClassNames(
				LumoUtility.Padding.Vertical.NONE,
				LumoUtility.Padding.Horizontal.MEDIUM);
		
		addToNavbar(header);
		
	}
	
	private void createDrawer() {
		addToDrawer(new VerticalLayout(
				new RouterLink("All tasks", AllTasksView.class),
				new RouterLink("Projects", AllProjectsView.class)
		));
	}
}
