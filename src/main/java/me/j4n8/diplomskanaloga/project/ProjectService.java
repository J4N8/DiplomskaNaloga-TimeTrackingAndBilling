package me.j4n8.diplomskanaloga.project;

import me.j4n8.diplomskanaloga.authentication.SecurityService;
import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
	private SecurityService securityService;
	private ProjectRepository projectRepository;
	
	public ProjectService(SecurityService securityService, ProjectRepository projectRepository) {
		this.securityService = securityService;
		this.projectRepository = projectRepository;
	}
	
	/***
	 * Creates a project for the authenticated user
	 * @param projectEntity Project to be created
	 * @return Created project
	 */
	public ProjectEntity createProject(ProjectEntity projectEntity) {
		projectEntity.setUser(securityService.getAuthenticatedUser());
		return projectRepository.save(projectEntity);
	}
	
	/***
	 * Updates a project
	 * @param projectEntity Project to be updated
	 * @return Updated project
	 */
	public ProjectEntity updateProject(ProjectEntity projectEntity) {
		return projectRepository.save(projectEntity);
	}
	
	/***
	 * Deletes a project
	 * @param projectEntity Project to be deleted
	 */
	public void deleteProject(ProjectEntity projectEntity) {
		projectRepository.delete(projectEntity);
	}
	
	/***
	 * Returns all projects for the authenticated user
	 * @return List of projects
	 */
	public List<ProjectEntity> findAll() {
		return projectRepository.findByUser_Id(securityService.getAuthenticatedUser().getId());
	}
	
	public ProjectEntity findById(Long id) {
		return projectRepository.findById(id).orElse(null);
	}
}
