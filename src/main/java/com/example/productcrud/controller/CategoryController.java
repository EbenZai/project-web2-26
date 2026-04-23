package com.example.productcrud.controller;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.CategoryRepository;
import com.example.productcrud.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;

    public CategoryController(CategoryRepository categoryRepo, UserRepository userRepo) {
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String list(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        model.addAttribute("categories", categoryRepo.findByUser(user));
        model.addAttribute("newCategory", new Category());
        return "category/list";
    }

    @PostMapping("/save")
    public String save(@AuthenticationPrincipal UserDetails ud, @ModelAttribute Category category) {
        User user = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        category.setUser(user); // Set pemilik kategori
        categoryRepo.save(category);
        return "redirect:/categories";
    }
}