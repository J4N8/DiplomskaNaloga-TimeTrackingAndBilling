package me.j4n8.diplomskanaloga.user;

import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	UserRepository userRepository;
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserEntity registerUser(String email, String password, String username) {
		UserEntity newUser = new UserEntity(null, username, email, passwordEncoder.encode(password), null, null);
		return userRepository.save(newUser);
	}
}
