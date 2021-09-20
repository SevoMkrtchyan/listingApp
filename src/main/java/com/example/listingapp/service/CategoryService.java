package com.example.listingapp.service;


import com.example.listingapp.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    boolean saveCategory(Category category);

    Category getCategoryById(int id);

    boolean deleteCategoryById(int id);

}
