package me.j4n8.diplomskanaloga.task;

import me.j4n8.diplomskanaloga.task.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}