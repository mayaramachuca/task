package com.task.task.core.mapper;

import com.task.task.app.dto.TaskUpdateDTO;
import com.task.task.app.dto.request.TaskRequestDTO;
import com.task.task.app.dto.response.TaskResponseDTO;
import com.task.task.core.builder.TaskBuilder;
import com.task.task.domain.entity.Task;
import com.task.task.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private TaskMapper(){

    }

    public Task toEntity(TaskRequestDTO taskRequest) {

        return TaskBuilder.builder()
                .status(taskRequest.status())
                .nome(taskRequest.nome())
                .build().toEntity();
    }

    public TaskResponseDTO toResponseDTO(Task task) {

        return TaskResponseDTO.builder()
                .nome(task.getNome())
                .status(task.getStatus())
                .id(task.getId())
                .build();
    }

    public List<TaskResponseDTO> toListDto(List<Task> tasks) {
        return tasks.stream()
                .map(task -> this.toResponseDTO(task))
                .collect(Collectors.toList());
    }

    public Task updateTaskToEntity (TaskUpdateDTO updateTask){

        return Task.builder()
                .id(updateTask.id())
                .nome(updateTask.nome())
                .status(updateTask.status())
                .build();
    }
}
