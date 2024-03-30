package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        // дополнительная проверка валидации, бизнес-логика проверок
        // должна быть проверка на то, существляет ли уже текущий пользователь, дропаться одно сообщение
        // должна быть проверка на то, существляет ли уже такая почта, дропаться другое сообщение
        // захешировать пароль, это тоже часть бизнес логики

        userRepository.save(user);
    }
}
