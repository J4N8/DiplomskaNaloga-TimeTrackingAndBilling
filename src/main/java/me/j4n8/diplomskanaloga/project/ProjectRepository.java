package me.j4n8.diplomskanaloga.project;

import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
	List<ProjectEntity> findByUsers_Id(Long id);
}