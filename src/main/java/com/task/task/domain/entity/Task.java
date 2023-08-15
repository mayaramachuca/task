package com.task.task.domain.entity;

import com.task.task.domain.constant.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Status status;
}
