package me.j4n8.diplomskanaloga.project;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import me.j4n8.diplomskanaloga.user.UserService;
import me.j4n8.diplomskanaloga.user.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
	private final SecurityService securityService;
	private final ProjectRepository projectRepository;
	private final UserService userService;
	
	public ProjectService(SecurityService securityService, ProjectRepository projectRepository, UserService userService) {
		this.securityService = securityService;
		this.projectRepository = projectRepository;
		this.userService = userService;
	}
	
	/***
	 * Creates a project for the authenticated user
	 * @param projectEntity Project to be created
	 * @return Created project
	 */
	public ProjectEntity createProject(ProjectEntity projectEntity) {
		projectEntity.setUsers(List.of(securityService.getAuthenticatedUser()));
		return projectRepository.save(projectEntity);
	}
	
	/***
	 * Updates a project
	 * @param projectEntity Project to be updated
	 * @return Updated project
	 */
	public ProjectEntity updateProject(ProjectEntity projectEntity) {
		if (securityService.hasNoProjectPermission(projectEntity)) {
			throw new IllegalArgumentException("You do not have permission to edit this project");
		}
		return projectRepository.save(projectEntity);
	}
	
	/***
	 * Deletes a project
	 * @param projectEntity Project to be deleted
	 */
	public void deleteProject(ProjectEntity projectEntity) {
		if (securityService.hasNoProjectPermission(projectEntity)) {
			throw new IllegalArgumentException("You do not have permission to delete this project");
		}
		projectRepository.delete(projectEntity);
	}
	
	/***
	 * Returns all projects for the authenticated user
	 * @return List of projects
	 */
	public List<ProjectEntity> findAll() {
		return projectRepository.findByUsers_Id(securityService.getAuthenticatedUser().getId());
	}
	
	public ProjectEntity findById(Long id) {
		ProjectEntity project = projectRepository.findById(id).orElse(null);
		if (securityService.hasNoProjectPermission(project)) {
			throw new IllegalArgumentException("You do not have permission to view this project");
		}
		return project;
	}
	
	public boolean inviteMember(ProjectEntity project, String email) throws IllegalArgumentException {
		if (securityService.hasNoProjectPermission(project)) {
			throw new IllegalArgumentException("You do not have permission to invite members to this project");
		}
		
		UserEntity user = userService.findByEmail(email);
		if (user == null) {
			throw new IllegalArgumentException("There is no user with that email address");
		}
		
		List<UserEntity> projectUsers = project.getUsers();
		
		for (UserEntity member : projectUsers) {
			if (member.getId().equals(user.getId())) {
				throw new IllegalArgumentException("User is already a member of this project");
			}
		}
		
		try {
			project.addUser(user);
			projectRepository.save(project);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<UserEntity> getMembers(ProjectEntity project) {
		if (securityService.hasNoProjectPermission(project)) {
			throw new IllegalArgumentException("You do not have permission to view members of this project");
		}
		return project.getUsers();
	}
	
	public void removeMember(UserEntity member, ProjectEntity project) {
		if (securityService.hasNoProjectPermission(project)) {
			throw new IllegalArgumentException("You do not have permission to remove members from this project");
		}
		try {
			project.removeUser(member);
			projectRepository.save(project);
		} catch (Exception e) {
		}
	}
}
