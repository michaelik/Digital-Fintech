package com.example.repository;

import com.example.dtos.response.CategoryDTO;
import com.example.entity.BillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillCategoryRepository extends JpaRepository<BillCategory, Long> {
    @Query("SELECT new com.example.dtos.response.CategoryDTO(u.id, u.name) FROM BillCategory u")
    List<CategoryDTO> findAllCategories();
}
