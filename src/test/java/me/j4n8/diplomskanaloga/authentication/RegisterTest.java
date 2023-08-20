package me.j4n8.diplomskanaloga.authentication;

import me.j4n8.diplomskanaloga.user.UserService;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class RegisterTest {
	@Autowired
	private DatabaseUserDetailsService databaseUserDetailsService;
	@Autowired
	private UserService userService;
	
	@Test
	public void testRegister() throws Exception {
		UserEntity testUser = new UserEntity(null, "testUser", "test@test.com", "testPassword", null, null);
		UserEntity registeredUser = userService.registerUser(testUser.getEmail(), testUser.getPassword(), testUser.getUsername());
		UserDetails loadedUser = databaseUserDetailsService.loadUserByUsername(testUser.getUsername());
		assert (loadedUser.getUsername().equals(registeredUser.getUsername()));
		assert (loadedUser.getPassword().equals(registeredUser.getPassword()));
	}
}
