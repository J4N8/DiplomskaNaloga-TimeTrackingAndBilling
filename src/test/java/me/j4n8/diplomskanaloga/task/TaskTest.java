package me.j4n8.diplomskanaloga.task;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.project.ProjectRepository;
import me.j4n8.diplomskanaloga.project.ProjectService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
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
public class TaskTest {
	@Autowired
	private TaskService taskService;
	
	@MockBean
	private SecurityService securityService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRepository userRepository;
	private ProjectEntity project;
	@Autowired
	private ProjectRepository projectRepository;
	
	@BeforeEach
	public void setUp() {
		UserEntity user = new UserEntity(1L, "testUser", "testPassword", "testEmail", null, null);
		userRepository.save(user);
		
		Mockito.when(securityService.getAuthenticatedUser())
				.thenReturn(user);
		
		ProjectEntity project1 = new ProjectEntity(1L, "testProject", "testDescription", List.of(securityService.getAuthenticatedUser()), null);
		project = projectRepository.save(project1);
	}
	
	@Test
	public void testCreateTask() throws Exception {
		UserEntity user = securityService.getAuthenticatedUser();
		projectService.createProject(project);
		TaskEntity task = new TaskEntity(null, "testTask", "testDescription", false, 0.0, user, project, null);
		TaskEntity createdTask = taskService.createTask(task);
		
		assert (createdTask.getTitle().equals(task.getTitle()));
		assert (createdTask.getDescription().equals(task.getDescription()));
		assert (createdTask.getProject().equals(task.getProject()));
		assert (createdTask.getUser().equals(task.getUser()));
		assert (createdTask.isCompleted() == task.isCompleted());
	}
	
	@Test
	public void testUpdateTask() throws Exception {
		TaskEntity task = new TaskEntity(null, "testTask", "testDescription", false, 0.0, null, project, null);
		TaskEntity createdTask = taskService.createTask(task);
		
		createdTask.setTitle("updatedTitle");
		createdTask.setDescription("updatedDescription");
		createdTask.setCompleted(true);
		
		TaskEntity updatedTask = taskService.save(createdTask);
		
		assert (updatedTask.getTitle().equals(createdTask.getTitle()));
		assert (updatedTask.getDescription().equals(createdTask.getDescription()));
		assert (updatedTask.isCompleted() == createdTask.isCompleted());
	}
	
	@Test
	public void testDeleteTask() throws Exception {
		TaskEntity task = new TaskEntity(null, "testTask", "testDescription", false, 0.0, null, project, null);
		TaskEntity createdTask = taskService.createTask(task);
		
		taskService.delete(createdTask);
		
		assert (taskService.findById(createdTask) == null);
	}
	
	@Test
	public void testFindAllByProject() throws Exception {
		UserEntity user = securityService.getAuthenticatedUser();
		ProjectEntity project1 = projectService.createProject(new ProjectEntity(1L, "project1", "testDescription", List.of(user), null));
		ProjectEntity project2 = projectService.createProject(new ProjectEntity(2L, "project2", "testDescription", List.of(user), null));
		
		taskService.createTask(new TaskEntity(null, "testTask1", "testDescription", false, 0.0, null, project1, null));
		taskService.createTask(new TaskEntity(null, "testTask2", "testDescription", false, 0.0, null, project2, null));
		
		List<TaskEntity> allByProject = taskService.findAllByProject(project1);
		allByProject.forEach(taskEntity -> {
			assert (taskEntity.getProject().getId().equals(project1.getId()));
		});
	}
}
