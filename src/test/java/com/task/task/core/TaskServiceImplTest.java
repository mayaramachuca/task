package com.task.task.core;

import com.task.task.core.serviceImpl.TaskServiceImpl;
import com.task.task.domain.constant.Status;
import com.task.task.domain.entity.Task;
import com.task.task.domain.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    public void CreateTaskIsOk() {
        Task taskInput = Task.builder()
                .nome("Teste ")
                .status(Status.DOING)
                .build();

        Task taskExpected = Task.builder()
                .nome("Teste ")
                .id(1l)
                .status(Status.DOING)
                .build();

        when(repository.save(taskInput)).thenReturn(taskExpected);
        Task response = service.createTask(taskInput);
        assertEquals(taskExpected, response);
    }

}
