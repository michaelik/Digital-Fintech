package com.example.repository;

import com.example.dtos.response.ProductDTO;
import com.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.example.dtos.response.ProductDTO(p.id, p.name) FROM Product p WHERE p.biller.id = :billerId")
    List<ProductDTO> findByBillerId(@Param("billerId") Long billerId);
}
