package com.example.service.impl;

import com.example.dtos.request.PaymentDTO;
import com.example.dtos.response.BillerDTO;
import com.example.dtos.response.CategoryDTO;
import com.example.dtos.response.ProductDTO;
import com.example.repository.BillCategoryRepository;
import com.example.repository.BillerRepository;
import com.example.repository.ProductRepository;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final BillCategoryRepository billCategoryRepository;
    private final BillerRepository billerRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CategoryDTO> getCategories() {
        return billCategoryRepository.findAllCategories();
    }

    @Override
    public List<BillerDTO> getBillerByCategory(long id) {
        return billerRepository.findByCategoryId(id);
    }

    @Override
    public List<ProductDTO> getProductByBiller(long id) {
        return productRepository.findByBillerId(id);
    }

    @Override
    public void submitPayment(PaymentDTO request) {
        /*
        * could not finish due to time constraints
        * */
    }

}
