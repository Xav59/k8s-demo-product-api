package com.xavier.productapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.xavier.productapi.dto.ProductModel;
import com.xavier.productapi.entity.Product;
import com.xavier.productapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(ProductModel product) {
        var productToSave = Product.builder()
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .build();

        return repository.save(productToSave);
    }

    public Product findById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Product> findAll() {
        return repository.findAll().stream().sorted(Comparator.comparing(Product::getId)).toList();
    }

    public void updateOne(long id, ProductModel product) {
        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException();
        repository.updateById(product.getDescription(), product.getCategory().toString(), product.getPrice(), id);
    }

    public Product patchOne(long id, JsonPatch patch) {
        var product = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        var productPatched = applyPatchToProduct(patch, product);
        return repository.save(productPatched);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private Product applyPatchToProduct(JsonPatch patch, Product product) {
        try {
            var objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(product, JsonNode.class));
            return objectMapper.treeToValue(patched, Product.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
