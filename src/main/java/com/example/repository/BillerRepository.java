package com.example.repository;

import com.example.dtos.response.BillerDTO;
import com.example.entity.Biller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillerRepository extends JpaRepository<Biller, Long> {
    @Query("SELECT new com.example.dtos.response.BillerDTO(b.id, b.name) FROM Biller b WHERE b.category.id = :categoryId")
    List<BillerDTO> findByCategoryId(@Param("categoryId") Long categoryId);
}
