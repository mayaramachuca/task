package com.task.task.app;

import com.task.task.app.controller.TaskController;
import com.task.task.app.dto.TaskUpdateDTO;
import com.task.task.app.dto.request.TaskRequestDTO;
import com.task.task.app.dto.response.TaskResponseDTO;
import com.task.task.core.mapper.TaskMapper;
import com.task.task.domain.constant.Status;
import com.task.task.domain.entity.Task;
import com.task.task.domain.exception.TaskNotFoundException;
import com.task.task.domain.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.Optional;

import static com.task.task.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private TaskService service;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void getAllTasksIsOk() throws Exception {
        Task task = Task.builder()
                .nome("teste1")
                .status(Status.CREATED)
                .build();

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                .id(1l)
                .nome("teste2")
                .status(Status.CREATED)
                .build();

        when(service.getAllTasks()).thenReturn(Arrays.asList(task));
        when(mapper.toListDto(anyList())).thenReturn(Arrays.asList(taskResponseDTO));

        mockMvc.perform(get("/tasks/lists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskResponseDTO.id()))
                .andExpect(jsonPath("$[0].nome").value(taskResponseDTO.nome()))
                .andExpect(jsonPath("$[0].status").value(taskResponseDTO.status().toString()))
                .andReturn();
    }

    @Test
    public void getTaskIsOk() throws Exception {
        Task task = Task.builder()
                .nome("teste1")
                .id(1l)
                .status(Status.CREATED)
                .build();

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                .id(1l)
                .nome("teste2")
                .status(Status.CREATED)
                .build();

        when(service.getTaskById(anyLong())).thenReturn(Optional.of(task));
        when(mapper.toResponseDTO(any())).thenReturn(taskResponseDTO);

        mockMvc.perform(get("/tasks/task/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskResponseDTO.id()))
                .andExpect(jsonPath("$.nome").value(taskResponseDTO.nome()))
                .andExpect(jsonPath("$.status").value(taskResponseDTO.status().toString()))
                .andReturn();
    }

    @Test
    public void getTaskNotOk() throws Exception {
        doThrow(TaskNotFoundException.class).when(service).getTaskById(anyLong());
        mockMvc.perform(get("/tasks/task/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void createTaskIsOk() throws Exception {
        Task task = Task.builder()
                .id(1l)
                .nome("Teste 1 ")
                .status(Status.CREATED)
                .build();

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                .nome("Teste Response")
                .id(1l)
                .status(Status.DOING)
                .build();

        TaskRequestDTO taskRequestDTO = TaskRequestDTO.builder()
                .nome("Teste Response")
                .status(Status.DOING)
                .build();

        when(service.createTask(any())).thenReturn(task);
        when(mapper.toEntity(any())).thenReturn(task);
        when(mapper.toResponseDTO(any())).thenReturn(taskResponseDTO);

        var content = asJsonString(taskRequestDTO);
        mockMvc.perform(post("/tasks/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskResponseDTO.id()))
                .andExpect(jsonPath("$.nome").value(taskResponseDTO.nome()))
                .andExpect(jsonPath("$.status").value(taskResponseDTO.status().toString()));
    }

    @Test
    public void createTaskIsNotOk() throws Exception {
        Task task = Task.builder()
                .id(1l)
                .nome("Teste 1 ")
                .status(Status.CREATED)
                .build();

        mockMvc.perform(post("/tasks/task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTaskIsOk() throws Exception {
        Task task = Task.builder()
                .id(1l)
                .nome("Teste 1 ")
                .status(Status.CREATED)
                .build();

        TaskResponseDTO taskResponseDTO = TaskResponseDTO.builder()
                .nome("Teste Response")
                .id(1l)
                .status(Status.DOING)
                .build();

        TaskUpdateDTO taskUpdateDTO = TaskUpdateDTO.builder()
                .nome("Teste Response")
                .id(1l)
                .status(Status.DOING)
                .build();

        when(service.updateTask(any())).thenReturn(task);
        when(mapper.updateTaskToEntity(any())).thenReturn(task);
        when(mapper.toResponseDTO(any())).thenReturn(taskResponseDTO);

        mockMvc.perform(put("/tasks/" + VALID_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(taskResponseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskResponseDTO.id()))
                .andExpect(jsonPath("$.nome").value(taskResponseDTO.nome()))
                .andExpect(jsonPath("$.status").value(taskResponseDTO.status().toString()));
    }

    @Test
    public void deleteTaskIsOk() throws Exception {

        doNothing().when(service).deletTask(VALID_ID);

        mockMvc.perform(delete("/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotFound() throws Exception {
        mockMvc.perform(delete("/tasks/error"))
                .andExpect(status().isBadRequest());
    }
}
