package com.example.owaspkryptering.Repositories;

import com.example.owaspkryptering.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

        User findByEmail(String email);
}