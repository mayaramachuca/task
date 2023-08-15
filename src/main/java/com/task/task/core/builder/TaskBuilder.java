package com.task.task.core.builder;

import com.task.task.domain.constant.Status;
import com.task.task.domain.entity.Task;
import lombok.Builder;
import lombok.Data;

@Builder
public record TaskBuilder(Long id, String nome, Status status) {

    public Task toEntity(){
        return new Task(id, nome, status);
    }
}
