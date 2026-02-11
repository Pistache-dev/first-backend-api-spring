package com.firstapi.api.task.dto;

import lombok.Data;

@Data
public class UpdateTaskRequest {

    private String title;
    private String description;
    private String status;
    private Long categoryId;

    // getters & setters
}
