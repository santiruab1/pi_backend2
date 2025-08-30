package com.example.pib2.service;

import com.example.pib2.model.dto.UserDTO;
import com.example.pib2.model.entity.User;
import com.example.pib2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setUserSurName(userDTO.getUserSurName());
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserCreatedAt(new Date());

        User newUser = userRepository.save(user);

        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(newUser.getId());
        newUserDTO.setFirstName(newUser.getFirstName());
        newUserDTO.setUserSurName(newUser.getUserSurName());
        newUserDTO.setUserEmail(newUser.getUserEmail());

        return newUserDTO;
    }
}
