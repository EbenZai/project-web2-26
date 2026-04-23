// src/main/java/com/example/productcrud/controller/UserController.java
package com.example.productcrud.controller;

import com.example.productcrud.dto.ChangePasswordRequest;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.UserRepository;
import com.example.productcrud.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // ── helper (sama persis seperti di ProductController) ──────────────────
    private User getCurrentUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }

    // ── Profile ─────────────────────────────────────────────────────────────
    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = getCurrentUser(userDetails);
        model.addAttribute("user", currentUser);
        return "user/profile";
    }

    // ── Change Password ──────────────────────────────────────────────────────
    @GetMapping("/profile/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());
        return "user/change-password";
    }

    @PostMapping("/profile/change-password")
    public String processChangePassword(
            @ModelAttribute ChangePasswordRequest changePasswordRequest,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        // Validasi: password baru tidak boleh kosong
        if (changePasswordRequest.getNewPassword() == null
                || changePasswordRequest.getNewPassword().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password baru tidak boleh kosong!");
            return "redirect:/profile/change-password";
        }

        // Validasi: konfirmasi password baru harus cocok
        if (!changePasswordRequest.getNewPassword()
                .equals(changePasswordRequest.getConfirmNewPassword())) {
            redirectAttributes.addFlashAttribute("error",
                    "Password baru dan konfirmasi password tidak cocok!");
            return "redirect:/profile/change-password";
        }

        User currentUser = getCurrentUser(userDetails);

        // Validasi: password lama harus sesuai + simpan jika valid
        boolean success = userService.changePassword(
                currentUser,
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword());

        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Password lama tidak sesuai!");
            return "redirect:/profile/change-password";
        }

        redirectAttributes.addFlashAttribute("success", "Password berhasil diubah!");
        return "redirect:/profile";
    }
}