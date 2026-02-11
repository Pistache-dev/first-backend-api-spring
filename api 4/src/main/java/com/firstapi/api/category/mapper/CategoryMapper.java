package com.firstapi.api.category.mapper;

import com.firstapi.api.category.dto.CategoryResponse;
import com.firstapi.api.category.entity.CategoryEntity;

public class CategoryMapper {

    private CategoryMapper() {
        // empêche l'instanciation
    }

    public static CategoryResponse toResponse(CategoryEntity category) {

        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    }
}
