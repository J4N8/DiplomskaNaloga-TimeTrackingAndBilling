package me.j4n8.diplomskanaloga.task;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.project.ProjectRepository;
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

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class TaskTest {
	@Autowired
	private TaskService taskService;
	
	@MockBean
	private SecurityService securityService;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;
	private ProjectEntity project;
	
	@BeforeEach
	public void setUp() {
		UserEntity user = new UserEntity(1L, "testUser", "testPassword", "testEmail", null, null);
		userRepository.save(user);
		
		Mockito.when(securityService.getAuthenticatedUser())
				.thenReturn(user);
		
		project = new ProjectEntity(1L, "testProject", "testDescription", securityService.getAuthenticatedUser(), null);
		projectRepository.save(project);
	}
	
	@Test
	public void testCreateTask() throws Exception {
		UserEntity user = securityService.getAuthenticatedUser();
		projectRepository.save(project);
		TaskEntity task = new TaskEntity(null, "testTask", "testDescription", false, user, project);
		TaskEntity createdTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getProject());
		
		assert (createdTask.getTitle().equals(task.getTitle()));
		assert (createdTask.getDescription().equals(task.getDescription()));
		assert (createdTask.getProject().equals(task.getProject()));
		assert (createdTask.getUser().equals(task.getUser()));
		assert (createdTask.isCompleted() == task.isCompleted());
	}
	
	@Test
	public void testUpdateTask() throws Exception {
		TaskEntity task = new TaskEntity(null, "testTask", "testDescription", false, null, project);
		TaskEntity createdTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getProject());
		
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
		TaskEntity task = new TaskEntity(null, "testTask", "testDescription", false, null, project);
		TaskEntity createdTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getProject());
		
		taskService.delete(createdTask);
		
		assert (taskService.findAllByAuthUser().isEmpty());
	}
	
	@Test
	public void testFindAllByProject() throws Exception {
		UserEntity user = securityService.getAuthenticatedUser();
		ProjectEntity project1 = new ProjectEntity(1L, "project1", "testDescription", user, null);
		projectRepository.save(project1);
		ProjectEntity project2 = new ProjectEntity(2L, "project2", "testDescription", user, null);
		projectRepository.save(project2);
		
		TaskEntity task1 = new TaskEntity(null, "testTask1", "testDescription", false, null, project1);
		TaskEntity createdTask1 = taskService.createTask(task1.getTitle(), task1.getDescription(), task1.getProject());
		TaskEntity task2 = new TaskEntity(null, "testTask2", "testDescription", false, null, project2);
		TaskEntity createdTask2 = taskService.createTask(task2.getTitle(), task2.getDescription(), task2.getProject());
		
		taskService.createTask(task1.getTitle(), task1.getDescription(), task1.getProject());
		taskService.createTask(task2.getTitle(), task2.getDescription(), task2.getProject());
		
		assert (taskService.findAllByProject(project1).size() == 2);
	}
}
