package org.tomodachi.service;

import org.tomodachi.exception.UserNotFoundException;
import org.tomodachi.model.User;
import org.tomodachi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User updateUser(UUID id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());  // Remember to encode the password properly.
            user.setRole(updatedUser.getRole());

            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    public void saveUser(User user) {
        // Encode the password before saving
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Set default role
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }

        userRepository.save(user);
    }

    public boolean deleteById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;  // User doesn't exist
        }
    }
}

