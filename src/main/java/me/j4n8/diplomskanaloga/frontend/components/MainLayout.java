package me.j4n8.diplomskanaloga.frontend.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.frontend.views.AllTasksView;
import me.j4n8.diplomskanaloga.frontend.views.HomepageView;

public class MainLayout extends AppLayout {
	private final SecurityService securityService;
	public MainLayout(SecurityService securityService) {
		this.securityService = securityService;
		createHeader();
		createDrawer();
	}
	
	private void createHeader() {
		H1 logo = new H1("Time tracking and billing app");
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
				new RouterLink("Home", HomepageView.class),
				new RouterLink("All tasks", AllTasksView.class)
		));
	}
}
