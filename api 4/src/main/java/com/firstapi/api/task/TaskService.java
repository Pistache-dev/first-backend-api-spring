package com.firstapi.api.task;

import com.firstapi.api.category.CategoryRepository;
import com.firstapi.api.category.entity.CategoryEntity;
import com.firstapi.api.category.exception.CategoryNotFoundException;
import com.firstapi.api.task.dto.CreateTaskRequest;
import com.firstapi.api.task.dto.TaskResponse;
import com.firstapi.api.task.dto.UpdateTaskRequest;
import com.firstapi.api.task.entity.TaskEntity;
import com.firstapi.api.task.entity.TaskStatus;
import com.firstapi.api.task.exception.TaskNotFoundException;
import com.firstapi.api.task.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository repository,
                       CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    // CREATE
    public TaskResponse create(CreateTaskRequest request) {

        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        TaskEntity task = new TaskEntity();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.TODO);
        task.setCategory(category);

        return TaskMapper.toResponse(repository.save(task));
    }

    // READ ALL
    public List<TaskResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    // READ BY ID
    public TaskResponse findById(Long id) {
        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return TaskMapper.toResponse(task);
    }

    // UPDATE
    public TaskResponse update(Long id, UpdateTaskRequest request) {

        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(request.getStatus()));
        }

        if (request.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));
            task.setCategory(category);
        }

        return TaskMapper.toResponse(repository.save(task));
    }

    // DELETE
    public void delete(Long id) {
        TaskEntity task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        repository.delete(task);
    }
}
