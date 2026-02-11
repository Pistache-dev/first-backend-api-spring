package com.firstapi.api.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long categoryId;
}
