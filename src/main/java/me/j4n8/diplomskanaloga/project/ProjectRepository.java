package me.j4n8.diplomskanaloga.project;

import me.j4n8.diplomskanaloga.project.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}