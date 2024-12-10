package com.SistemPos.sistemapos.services.products;

import com.SistemPos.sistemapos.dto.request.ProductRequest;
import com.SistemPos.sistemapos.dto.response.ProductResponse;
import com.SistemPos.sistemapos.exception.DuplicateProductException;
import com.SistemPos.sistemapos.models.products.ProductsEntity;
import com.SistemPos.sistemapos.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductsRepository productsRepository;

    public ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public ProductResponse saveProduct(ProductRequest productRequest) {
        if (productsRepository.existsByName(productRequest.getName())) {
            throw new DuplicateProductException("Product with name " + productRequest.getName() + " already exists.");
        }
        ProductsEntity productsEntity = mapToEntity(productRequest);
        ProductsEntity savedEntity = productsRepository.save(productsEntity);

        return new ProductResponse("Product added successfully", savedEntity);
    }
    public List<ProductResponse> getAllProducts() {
        List<ProductsEntity> products = productsRepository.findAll();
        return products.stream()
                .distinct()
                .map(this::mapToProductResponse)
                .toList();
    }
    private ProductResponse mapToProductResponse(ProductsEntity entity) {
        return new ProductResponse(
                "Product retrieved successfully",
                entity.getName(),
                entity.getDescription(),
                entity.getPrice().doubleValue(),
                entity.getStock()
        );
    }
    private ProductsEntity mapToEntity(ProductRequest productRequest) {
        ProductsEntity productsEntity = new ProductsEntity();
        productsEntity.setName(productRequest.getName());
        productsEntity.setDescription(productRequest.getDescription());
        productsEntity.setPrice(productRequest.getPrice());
        productsEntity.setStock(productRequest.getStock());
        return productsEntity;
    }
}
