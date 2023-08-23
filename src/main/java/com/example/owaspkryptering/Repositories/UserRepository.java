package com.example.owaspkryptering.Repositories;

import com.example.owaspkryptering.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

        User findByEmail(String email);
}
