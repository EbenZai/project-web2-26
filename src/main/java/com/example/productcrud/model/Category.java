package com.example.productcrud.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories") // Spring akan membuatkan tabel 'categories' di database
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    // Menghubungkan kategori ke User (Pemilik)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relasi ke produk (Satu kategori bisa punya banyak produk)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    // Konstruktor Kosong (Wajib untuk JPA)
    public Category() {}

    // Getter dan Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}