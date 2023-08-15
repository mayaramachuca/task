package com.task.task.app.controller;

import com.task.task.app.dto.TaskUpdateDTO;
import com.task.task.app.dto.request.TaskRequestDTO;
import com.task.task.app.dto.response.TaskResponseDTO;
import com.task.task.core.mapper.TaskMapper;
import com.task.task.domain.entity.Task;
import com.task.task.domain.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    public TaskMapper taskMapper;

    @Autowired
    public TaskService service;

    @PostMapping(value = "task")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Task task = service.createTask(taskMapper.toEntity(taskRequestDTO));
        return taskMapper.toResponseDTO(task);
    }

    @GetMapping(value = "/task/{id}")
    public Optional<TaskResponseDTO> getTask (@PathVariable("id")Long id) {
        Optional<Task> task =  service.getTaskById(id);
        var taskResponseDTO = taskMapper.toResponseDTO(task.get());
        return Optional.of(taskResponseDTO);
    }

    @GetMapping(value = "lists")
    public List<TaskResponseDTO> listTasks(){
        List<Task> tasks = service.getAllTasks();
        return taskMapper.toListDto(tasks);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskUpdateDTO taskUpdate){
        var taskUp = service.updateTask(taskMapper.updateTaskToEntity(taskUpdate));
        var taskResponse = taskMapper.toResponseDTO(taskUp);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> delete (@PathVariable Long id) {
        service.deletTask(id);
        // resposta HTTP é construída usando ResponseEntity c/ método noContent() é usado para criar uma resposta sem conteúdo,com código
        // de status 204 No Content. Isso indica que a exclusão foi bem-sucedida e não há conteúdo para retornar no corpo da resposta.
        return ResponseEntity.noContent().build();
    }

}
