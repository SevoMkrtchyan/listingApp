package com.example.listingapp.service.impl;

import com.example.listingapp.entity.Category;
import com.example.listingapp.repository.CategoryRepository;
import com.example.listingapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean saveCategory(Category category) {
        if (!categoryRepository.findByName(category.getName().toUpperCase()).isPresent()) {
            category.setName(category.getName().toUpperCase());
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public Category findCategoryById(int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public boolean deleteCategoryById(int id) {
        if (findCategoryById(id) != null) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Category findByName(String name) {
        Optional<Category> byName = categoryRepository.findByName(name);
        return byName.orElse(null);
    }

}
