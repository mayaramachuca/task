package com.task.task.app.dto;

import com.task.task.domain.constant.Status;
import lombok.Builder;

@Builder
public record TaskUpdateDTO(Long id, String nome, Status status) {
}
