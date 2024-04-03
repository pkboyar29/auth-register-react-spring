package com.example.backend.services;

import com.example.backend.dto.AuthRequestDTO;
import com.example.backend.exceptions.AuthenticationFailedException;
import com.example.backend.exceptions.DuplicateUserException;
import com.example.backend.exceptions.ObjectNotFoundException;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public void saveUser(User user) throws DuplicateUserException {
        // дополнительная проверка валидации, бизнес-логика проверок

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUserException("DUPLICATE_USERNAME", "User with this username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException("DUPLICATE_EMAIL", "User with this email already exists");
        }

        userRepository.save(user);
    }

    public void verifyCredentials(AuthRequestDTO authRequestDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(authRequestDTO.getUsername());

        if (optionalUser.isEmpty()) {
            throw new AuthenticationFailedException("DUPLICATE_USERNAME", "User with this username doesn't exists");
        }

        User user = optionalUser.get();
        String rawPassword = authRequestDTO.getPassword();
        String encodedPasswordFromDB = user.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPasswordFromDB)) {
            throw new AuthenticationFailedException("INVALID_PASSWORD", "Password and username don't match");
        }
    }

    public Map<String, String> getUserDataByUserName(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new ObjectNotFoundException("User with this id don't exist");
        }

        User user = optionalUser.get();
        Map<String, String> data = new HashMap<>();
        data.put("first-name", user.getFirstName());
        data.put("theme", user.getTheme());
        return data;
    }

    public void changeUserTheme(String username, String theme) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new ObjectNotFoundException("User with this id don't exist");
        }

        User user = optionalUser.get();
        user.setTheme(theme);
        userRepository.save(user);
    }
}
