package com.example.listingapp.repository;

import com.example.listingapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

}
