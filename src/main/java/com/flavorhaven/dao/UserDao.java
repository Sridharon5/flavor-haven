package com.flavorhaven.dao;

import com.flavorhaven.entity.UserEntity;
import java.util.Optional;

public interface UserDao {
    UserEntity saveUser(UserEntity user);
    Optional<UserEntity> findByUsername(String username);
}
