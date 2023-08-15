package com.task.task.domain.service;

import com.task.task.domain.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(Task task);
    Optional<Task> getTaskById(Long id);
    List<Task> getAllTasks();
    Task updateTask(Task task);
    void deletTask(Long id);

}
