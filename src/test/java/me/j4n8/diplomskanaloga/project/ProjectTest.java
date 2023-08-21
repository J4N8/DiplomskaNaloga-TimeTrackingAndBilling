package me.j4n8.diplomskanaloga.project;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.user.UserRepository;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class ProjectTest {
	@Autowired
	private ProjectService projectService;
	
	@MockBean
	private SecurityService securityService;
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	public void setUp() {
		UserEntity user = new UserEntity(1L, "testUser", "testPassword", "testEmail", null, null);
		userRepository.save(user);
		
		Mockito.when(securityService.getAuthenticatedUser())
				.thenReturn(user);
	}
	
	@Test
	public void testCreateProject() {
		ProjectEntity project = new ProjectEntity(null, "Test project", "Test description", List.of(securityService.getAuthenticatedUser()), null);
		ProjectEntity createdProject = projectService.createProject(project);
		
		assert (createdProject.getName().equals(project.getName()));
		assert (createdProject.getDescription().equals(project.getDescription()));
	}
	
	@Test
	public void testUpdateProject() {
		ProjectEntity project = new ProjectEntity(null, "Test project", "Test description", List.of(securityService.getAuthenticatedUser()), null);
		ProjectEntity createdProject = projectService.createProject(project);
		
		createdProject.setName("Updated project");
		createdProject.setDescription("Updated description");
		ProjectEntity updatedProject = projectService.updateProject(createdProject);
		
		assert (updatedProject.getName().equals(createdProject.getName()));
		assert (updatedProject.getDescription().equals(createdProject.getDescription()));
	}
	
	@Test
	public void testDeleteProject() {
		ProjectEntity project = new ProjectEntity(null, "Test project", "Test description", List.of(securityService.getAuthenticatedUser()), null);
		ProjectEntity createdProject = projectService.createProject(project);
		
		projectService.deleteProject(createdProject);
		
		assert (projectService.findById(createdProject.getId()) == null);
	}
	
	@Test
	public void testFindAll() {
		ProjectEntity project1 = new ProjectEntity(null, "Test project1", "Test description", List.of(securityService.getAuthenticatedUser()), null);
		ProjectEntity project2 = new ProjectEntity(null, "Test project2", "Test description", List.of(securityService.getAuthenticatedUser()), null);
		projectService.createProject(project1);
		projectService.createProject(project2);
		
		assert (projectService.findAll().size() == 2);
	}
}
