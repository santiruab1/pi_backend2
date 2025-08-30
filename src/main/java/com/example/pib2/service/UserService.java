package com.example.pib2.service;

import com.example.pib2.model.dto.UserDTO;
import com.example.pib2.model.entity.User;
import com.example.pib2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setIdentificationNumber(userDTO.getIdentificationNumber());
        user.setFirstName(userDTO.getFirstName());
        user.setUserSurName(userDTO.getUserSurName());
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserCreatedAt(new Date());

        User newUser = userRepository.save(user);

        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(newUser.getId());
        newUserDTO.setIdentificationNumber(newUser.getIdentificationNumber());
        newUserDTO.setFirstName(newUser.getFirstName());
        newUserDTO.setUserSurName(newUser.getUserSurName());
        newUserDTO.setUserEmail(newUser.getUserEmail());

        return newUserDTO;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }
    
    public UserDTO getUserByidentificationNumber(String identificationNumber) {
        User user = userRepository.findByIdentificationNumber(identificationNumber);
        return user != null ? convertToDTO(user) : null;
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByUserEmail(email);
        return user != null ? convertToDTO(user) : null;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setIdentificationNumber(user.getIdentificationNumber());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setUserSurName(user.getUserSurName());
        userDTO.setUserEmail(user.getUserEmail());
        return userDTO;
    }
}
