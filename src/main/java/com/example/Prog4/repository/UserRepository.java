package com.example.Prog4.repository;

import com.example.Prog4.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity , Long> {
    @Query(value = "SELECT userr FROM userr WHERE username = ':username' " +
            "AND email = :email " +
            "AND password = ':password' "
            ,nativeQuery = true)
    UserEntity checkUser(@Param("username") String username ,@Param("email") String email ,@Param("password") String password);

    UserEntity findByUsername(String username);
}
