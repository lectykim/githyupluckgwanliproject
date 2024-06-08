package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmailAndPw(String email,String pw);
    Boolean existsUserByEmail(String email);
}
