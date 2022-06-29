package com.project.kantinkejujuran.repository;

import com.project.kantinkejujuran.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(value = "SELECT * FROM product ORDER BY date_created DESC, name ASC", nativeQuery = true)
    @NonNull
    List<Product> findAll();
}