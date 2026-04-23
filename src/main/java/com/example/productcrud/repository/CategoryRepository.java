package com.example.productcrud.repository;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Mencari semua kategori milik user tertentu saja
    List<Category> findByUser(User user);

    // Mencari satu kategori berdasarkan ID dan memastikan itu milik user tersebut
    Optional<Category> findByIdAndUser(Long id, User user);
}