package com.flavorhaven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flavorhaven.entity.UserLikedRecipeEntity;
import com.flavorhaven.entity.UserEntity;

public interface UserLikedRecipeRepository extends JpaRepository<UserLikedRecipeEntity, Long> {

	List<UserLikedRecipeEntity> findByUser(UserEntity user);

	UserLikedRecipeEntity findByUserAndRecipeId(UserEntity user, Long recipeId);

	Optional<UserLikedRecipeEntity> findByUserIdAndRecipeId(Long userId, Long recipeId);
}
