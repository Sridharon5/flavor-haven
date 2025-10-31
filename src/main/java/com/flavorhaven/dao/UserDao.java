package com.flavorhaven.dao;

import com.flavorhaven.dto.UserLikedRecipeDto;
import com.flavorhaven.entity.UserEntity;

import java.util.Map;
import java.util.Optional;

public interface UserDao {
    UserEntity saveUser(UserEntity user);
    Optional<UserEntity> findByUsername(String username);
	
	boolean saveLikedRecipe(UserLikedRecipeDto recipeDto);
	Map<String, Object> fetchLikedRecipesByUser(Long userId);
	Map<String, Object> removeLikedRecipe(UserLikedRecipeDto recipeDto);
}
