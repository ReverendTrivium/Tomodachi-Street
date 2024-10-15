package org.tomodachi.controllers.admin;

import org.tomodachi.exception.UserNotFoundException;
import org.tomodachi.model.User;
import org.tomodachi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable UUID id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        model.addAttribute("user", user);
        return "admin/edit_user";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable UUID id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        boolean deleted = userService.deleteById(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return "redirect:/admin/users";
    }

    // Add an exception handler for UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404-User";
    }
}

