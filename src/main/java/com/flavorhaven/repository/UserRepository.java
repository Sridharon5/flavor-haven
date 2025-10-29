package com.flavorhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flavorhaven.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
