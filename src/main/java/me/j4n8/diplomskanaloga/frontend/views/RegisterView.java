package me.j4n8.diplomskanaloga.frontend.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.j4n8.diplomskanaloga.frontend.components.register.RegisterForm;
import me.j4n8.diplomskanaloga.user.UserService;

@Route(value = "register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
	private UserService userService;
	public RegisterView(UserService userService){
		this.userService = userService;
		RegisterForm registerForm = new RegisterForm(this.userService);
		addClassName("register-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		add(registerForm);
	}
}
