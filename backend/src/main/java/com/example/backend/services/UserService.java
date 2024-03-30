package com.example.backend.services;

import com.example.backend.dto.AuthRequestDTO;
import com.example.backend.exceptions.DublicateUserException;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void saveUser(User user) throws DublicateUserException {
        // дополнительная проверка валидации, бизнес-логика проверок

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DublicateUserException("User with this username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DublicateUserException("User with this email already exists");
        }

        userRepository.save(user);
    }

    public void verifyCredentials(AuthRequestDTO authRequestDTO) {
        if (!userRepository.existsByUsername(authRequestDTO.getUsername())) {
            throw new DublicateUserException("User with this username doesn't exists");
        }

        User user = userRepository.findByUsername(authRequestDTO.getUsername());
        String rawPassword = authRequestDTO.getPassword();
        String encodedPasswordFromDB = user.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPasswordFromDB)) {
            throw new DublicateUserException("Password and username don't match");
        }
    }
}
