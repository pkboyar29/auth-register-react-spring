package com.example.backend.services;

import com.example.backend.dto.AuthRequestDTO;
import com.example.backend.exceptions.DublicateUserException;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) throws DublicateUserException {
        // дополнительная проверка валидации, бизнес-логика проверок
        // захешировать пароль, это тоже часть бизнес логики

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

        if (!userRepository.checkPasswordByUsername(authRequestDTO.getUsername(), authRequestDTO.getPassword())) {
            throw new DublicateUserException("Password and username don't match");
        }
    }
}
