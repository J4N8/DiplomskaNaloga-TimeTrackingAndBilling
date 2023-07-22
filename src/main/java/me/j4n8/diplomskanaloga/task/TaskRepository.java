package me.j4n8.diplomskanaloga.task;

import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}