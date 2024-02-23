package com.xavier.productapi.dto;

import com.xavier.productapi.enums.ProductCategory;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Component
public class ProductModel {

    private ProductCategory category;
    private String description;
    private BigDecimal price;
}
