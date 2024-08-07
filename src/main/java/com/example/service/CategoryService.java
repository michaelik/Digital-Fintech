package com.example.service;

import com.example.dtos.request.PaymentDTO;
import com.example.dtos.response.BillerDTO;
import com.example.dtos.response.CategoryDTO;
import com.example.dtos.response.ProductDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getCategories();
    List<BillerDTO> getBillerByCategory(long categoryId);

    List<ProductDTO> getProductByBiller(long billerId);

    void submitPayment(PaymentDTO request);
}
