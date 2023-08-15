package com.task.task.app.dto.request;

import com.task.task.domain.constant.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record TaskRequestDTO(@NotBlank String nome, Status status) {

}
