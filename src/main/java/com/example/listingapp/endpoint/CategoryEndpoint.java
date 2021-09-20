package com.example.listingapp.endpoint;

import com.example.listingapp.entity.Category;
import com.example.listingapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/category")
public class CategoryEndpoint {

    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @PostMapping()
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        if (categoryService.saveCategory(category)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Category> deleteCategoryById(@PathVariable(name = "id") int id) {
        if (categoryService.deleteCategoryById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "id") int id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping()
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        if (category != null) {
            Category fromDB = getCategoryById(category.getId()).getBody();
            if (fromDB != null) {
                if (fromDB.getId() == category.getId() && fromDB.getName().equalsIgnoreCase(category.getName())) {
                    fromDB.setName(category.getName());
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


}