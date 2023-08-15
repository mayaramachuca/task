package com.task.task.app.dto.response;

import com.task.task.domain.constant.Status;
import lombok.*;

//equals, hashCode e toString automaticamente gerados aos records
@Builder
public record TaskResponseDTO (Long id, String nome, Status status) {
}
