package com.example.pib2.service;

import com.example.pib2.model.dto.UserDTO;
import com.example.pib2.model.dto.UserCreateDTO;
import com.example.pib2.model.entity.User;
import com.example.pib2.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setIdentificationNumber(userCreateDTO.getIdentificationNumber());
        user.setFirstName(userCreateDTO.getFirstName());
        user.setUserSurName(userCreateDTO.getUserSurName());
        user.setUserEmail(userCreateDTO.getUserEmail());
        user.setUserPassword(userCreateDTO.getUserPassword());
        user.setUserPhoneNumber(userCreateDTO.getUserPhoneNumber());
        // createdAt se establece automáticamente por @CreationTimestamp

        User newUser = userRepository.save(user);
        return convertToDTO(newUser);
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

    public UserDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIdentificationNumber(userCreateDTO.getIdentificationNumber());
            user.setFirstName(userCreateDTO.getFirstName());
            user.setUserSurName(userCreateDTO.getUserSurName());
            user.setUserEmail(userCreateDTO.getUserEmail());
            user.setUserPassword(userCreateDTO.getUserPassword());
            user.setUserPhoneNumber(userCreateDTO.getUserPhoneNumber());
            
            User updatedUser = userRepository.save(user);
            return convertToDTO(updatedUser);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setIdentificationNumber(user.getIdentificationNumber());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setUserSurName(user.getUserSurName());
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setUserPhoneNumber(user.getUserPhoneNumber());
        userDTO.setUserCreatedAt(user.getCreatedAt() != null ? 
            java.sql.Timestamp.valueOf(user.getCreatedAt()) : null);
        return userDTO;
    }
}
