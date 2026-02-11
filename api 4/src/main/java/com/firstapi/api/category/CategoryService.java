package com.firstapi.api.category;

import com.firstapi.api.category.dto.CategoryResponse;
import com.firstapi.api.category.dto.CreateCategoryRequest;
import com.firstapi.api.category.dto.UpdateCategoryRequest;
import com.firstapi.api.category.entity.CategoryEntity;
import com.firstapi.api.category.exception.CategoryNotFoundException;
import com.firstapi.api.category.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public CategoryResponse create(CreateCategoryRequest request) {

        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());

        return CategoryMapper.toResponse(repository.save(category));
    }

    // READ ALL
    public List<CategoryResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    // READ BY ID
    public CategoryResponse findById(Long id) {
        CategoryEntity category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return CategoryMapper.toResponse(category);
    }

    // UPDATE
    public CategoryResponse update(Long id, UpdateCategoryRequest request) {

        CategoryEntity category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (request.getName() != null) {
            category.setName(request.getName());
        }

        return CategoryMapper.toResponse(repository.save(category));
    }

    // DELETE
    public void delete(Long id) {
        CategoryEntity category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        repository.delete(category);
    }
}
