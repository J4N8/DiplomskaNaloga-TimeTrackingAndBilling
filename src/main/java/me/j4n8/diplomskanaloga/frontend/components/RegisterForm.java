package me.j4n8.diplomskanaloga.frontend.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import me.j4n8.diplomskanaloga.user.UserService;

@SpringComponent
public class RegisterForm extends VerticalLayout {
	private UserService userService;
	
	private H1 title;
	private Button registerButton;
	private TextField username;
	private TextField email;
	private PasswordField password;
	private PasswordField confirmPassword;
	
	public RegisterForm(UserService userService) {
		this.userService = userService;
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		title = new H1("Register");
		username = new TextField("Username");
		email = new TextField("Email");
		password = new PasswordField("Password");
		confirmPassword = new PasswordField("Confirm password");
		registerButton = new Button("Register");
		
		registerButton.addClickListener(event -> {
			if (!password.getValue().equals(confirmPassword.getValue())){
				return;
			}
			register(username.getValue(), email.getValue(), password.getValue(), confirmPassword.getValue());
		});
		
		add(title, username, email, password, confirmPassword, registerButton);
	}
	
	public void register(String username, String email, String password, String confirmPassword) {
		try {
			userService.registerUser(email, password, confirmPassword, username);
			UI.getCurrent().navigate("login");
		}
		catch (IllegalArgumentException e) {
			System.out.println("Passwords do not match");
		}
	}
}
