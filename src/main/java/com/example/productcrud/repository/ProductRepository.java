package com.example.productcrud.repository;

import com.example.productcrud.model.Product;
import com.example.productcrud.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOwner(User owner);
    Optional<Product> findByIdAndOwner(Long id, User owner);


    Page<Product> findByOwnerAndNameContainingIgnoreCase(User owner, String name, Pageable pageable);


    Page<Product> findByOwnerAndNameContainingIgnoreCaseAndCategory_Id(User owner, String name, Long categoryId, Pageable pageable);
}