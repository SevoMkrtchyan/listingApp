package com.example.listingapp.repository;

import com.example.listingapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE SET listing t1 t1.category_id=null WHERE t1.category_id=:id " +
//            " delete from category t2 where t2.id=:id"
//            , nativeQuery = true)
//    void deleteCategoryById(@Param("id") int id);

}
