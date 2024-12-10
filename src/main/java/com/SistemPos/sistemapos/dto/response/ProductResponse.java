package com.SistemPos.sistemapos.dto.response;

import com.SistemPos.sistemapos.models.products.ProductsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductResponse {
    private String message;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

    public ProductResponse(String message, ProductsEntity product) {
        this.message = message;
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice().doubleValue();
        this.stock = product.getStock();
    }

}
