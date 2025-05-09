package com.exam.ms_auth.repository;

import com.exam.ms_auth.entity.Rol;
import com.exam.ms_auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRol(Rol rol);
}