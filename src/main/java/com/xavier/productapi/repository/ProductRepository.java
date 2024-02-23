package com.xavier.productapi.repository;

import com.xavier.productapi.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET description = ?1, category = ?2, price = ?3 WHERE id = ?4", nativeQuery = true)
    void updateById(String description, String category, BigDecimal price, long id);

}
