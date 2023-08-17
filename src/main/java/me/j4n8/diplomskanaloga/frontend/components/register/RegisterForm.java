package me.j4n8.diplomskanaloga.frontend.components.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import me.j4n8.diplomskanaloga.user.UserService;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;

@SpringComponent
public class RegisterForm extends VerticalLayout {
	private final UserService userService;
	
	private UserEntity user = new UserEntity();
	
	private H1 title;
	private Button registerButton;
	private TextField username;
	private TextField email;
	private PasswordField password;
	private PasswordField confirmPassword;
	private Binder<UserEntity> binder;
	
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
			register();
		});
		
		add(title, username, email, password, confirmPassword, registerButton);
		
		validation();
	}
	
	private void validation() {
		binder = new Binder<>(UserEntity.class);
		binder.forField(username)
				.asRequired("Username is required")
				.withValidator(username -> username.length() >= 4, "Username must be at least 4 characters long")
				.bind(UserEntity::getUsername, UserEntity::setUsername);
		binder.forField(email)
				.asRequired("Email is required")
				.withValidator(new EmailValidator("Invalid email address"))
				.bind(UserEntity::getEmail, UserEntity::setEmail);
		binder.forField(password)
				.asRequired("Password is required")
				.withValidator(password -> password.length() >= 8, "Password must be at least 8 characters long")
				.bind(UserEntity::getPassword, UserEntity::setPassword);
		binder.forField(confirmPassword)
				.asRequired("Confirm password is required")
				.withValidator(confirmPassword -> confirmPassword.equals(password.getValue()), "Passwords do not match")
				.bind(UserEntity::getPassword, UserEntity::setPassword);
	}
	
	public void register() {
		try {
			binder.writeBean(user);
			userService.registerUser(user.getEmail(), user.getPassword(), user.getUsername());
			UI.getCurrent().navigate("login");
		} catch (ValidationException e) {
			Notification.show("Please fill all fields correctly and try again");
		}
	}
}
