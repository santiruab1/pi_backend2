package com.example.pib2.repository;

import com.example.pib2.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String userEmail);
    User findByFirstName(String firstName);
    User findByIdentificationNumber(String identificationNumber);
}