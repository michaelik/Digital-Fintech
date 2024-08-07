package com.example.controller.category;

import com.example.constant.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.request.PaymentDTO;
import com.example.dtos.response.BillerDTO;
import com.example.dtos.response.CategoryDTO;
import com.example.dtos.response.ProductDTO;
import com.example.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/categories")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<List<CategoryDTO>> getCategories() {
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.CATEGORIES_RETRIEVED_SUCCESSFULLY,
                categoryService.getCategories()
        );
    }

    @GetMapping(
            path = "/biller/{categoryId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<List<BillerDTO>> getBillerByCategory(
            @PathVariable
            long categoryId
    ) {
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.BILLER_RETRIEVED_SUCCESSFULLY,
                categoryService.getBillerByCategory(categoryId)
        );
    }

    @GetMapping(
            path = "/product/{billerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<List<ProductDTO>> getProductByCategory(
            @PathVariable
            long billerId
    ) {
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.BILLER_RETRIEVED_SUCCESSFULLY,
                categoryService.getProductByBiller(billerId)
        );
    }

    @PostMapping(
            path = "/product/payment",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<?> submitPayment(
            @RequestBody @Valid
            PaymentDTO request
    ) {
        categoryService.submitPayment(request);
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.PAYMENT_MADE_SUCCESSFULLY
        );
    }
}
