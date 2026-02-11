package com.firstapi.api.task.mapper;

import com.firstapi.api.task.dto.TaskResponse;
import com.firstapi.api.task.entity.TaskEntity;

public class TaskMapper {

    private TaskMapper() {
        // empêche l'instanciation
    }

    public static TaskResponse toResponse(TaskEntity task) {

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus().name());

        response.setCategoryId(task.getCategory().getId());
        response.setCategoryName(task.getCategory().getName());

        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }
}
