package com.task.task.core.serviceImpl;

import com.task.task.domain.constant.Status;
import com.task.task.domain.entity.Task;
import com.task.task.domain.exception.TaskNotFoundException;
import com.task.task.domain.repository.TaskRepository;
import com.task.task.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;

    @Override
    public Task createTask(Task task) {

        task.setStatus(Status.CREATED);
        return repository.save(task);
    }

    @Override
    public Optional<Task>  getTaskById(Long id) {

        Optional<Task> taskOp = repository.findById(id);
        if(taskOp.isEmpty()){
            throw new TaskNotFoundException();
        }
        return taskOp;
    }

    @Override
    public List<Task> getAllTasks() {

        return repository.findAll();
    }

    @Override
    public Task updateTask(Task task) {
        var taskUp = repository.findById(task.getId()).get();
        task.setNome(taskUp.getNome());
        task.setStatus(taskUp.getStatus());
        return repository.save(task);
    }

    @Override
    public void deletTask(Long id) {
        Task task = repository.getReferenceById(id);
        repository.deleteById(id);
    }




}
