package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	private final LoginForm loginForm = new LoginForm();
	public LoginView() {
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		loginForm.setAction("login");
		loginForm.setForgotPasswordButtonVisible(false);
		
		add(new H1("Time tracking and billing app"), loginForm);
		add(new RouterLink("register", RegisterView.class));
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters()
				.containsKey("error")) {
			loginForm.setError(true);
		}
	}
}
