// =====================================================================
// UPDATE SecurityConfig.java
// Tambahkan "/change-password" dan "/profile" ke authenticated routes
// (sudah ter-cover oleh .anyRequest().authenticated() - tidak perlu ubah)
//
// Jika ada ProfileController, pastikan route /profile sudah ada.
// Contoh ProfileController minimal:
// =====================================================================

package com.example.productcrud.controller;

import com.example.productcrud.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        return "profile";
    }
}