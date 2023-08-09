package me.j4n8.diplomskanaloga.task;

import me.j4n8.diplomskanaloga.task.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
	@Query("select t from TaskEntity t where t.user.id = ?1")
	List<TaskEntity> findByUser_Id(@NonNull Long id);
}