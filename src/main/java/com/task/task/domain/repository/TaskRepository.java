package com.task.task.domain.repository;

import com.task.task.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //spring boot 3 @ opcional de repository
public interface TaskRepository extends JpaRepository<Task,Long> {

}
