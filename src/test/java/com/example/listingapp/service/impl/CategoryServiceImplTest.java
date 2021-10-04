package com.example.listingapp.service.impl;

import com.example.listingapp.entity.Category;
import com.example.listingapp.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void saveCategory() {
        boolean category = categoryService.saveCategory(Category.builder()
                .name("testCategory")
                .build());
        assertTrue(category);
    }

    @Test
    void saveCategory_ifNameNull() {
        boolean category = categoryService.saveCategory(Category.builder()
                .name(null)
                .build());
        assertFalse(category);
    }

    @Test
    void saveCategory_ifNameEmpty() {
        boolean category = categoryService.saveCategory(Category.builder()
                .name("")
                .build());
        assertFalse(category);
    }

    @Test
    void findCategoryById_not_null() {
        Category byId = categoryService.findCategoryById(5);
        assertNotNull(byId);
    }

    @Test
    void findCategoryById_if_not_exist() {
        Category byId = categoryService.findCategoryById(266);
        assertNull(byId);
    }

    @Test
    void deleteCategoryById_ifExist() {
        boolean result = categoryService.deleteCategoryById(1);
        assertTrue(result);
    }

    @Test
    void deleteCategoryById_ifNotExist() {
        boolean result = categoryService.deleteCategoryById(1);
        assertFalse(result);
    }

    @Test
    void findByName_ifNull() {
        Category byName = categoryService.findByName(null);
        assertNull(byName);
    }

    @Test
    void findByName_ifEmpty() {
        Category byName = categoryService.findByName("");
        assertNull(byName);
    }

    @Test
    void findByName_ifExist() {
        Category byName = categoryService.findByName("testCategory");
        assertNotNull(byName);
    }

    @Test
    void findByName_ifNotExist() {
        Category byName = categoryService.findByName("testCat55egory");
        assertNull(byName);
    }

}