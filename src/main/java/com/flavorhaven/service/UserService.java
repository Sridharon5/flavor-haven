package com.flavorhaven.service;

import com.flavorhaven.dto.UserSignupDto;

import java.util.Map;

import com.flavorhaven.dto.UserLikedRecipeDto;
import com.flavorhaven.dto.UserLoginDto;
import com.flavorhaven.entity.UserEntity;

public interface UserService {
    String signup(UserSignupDto userSignupDto);
    Map<String, Object> login(UserLoginDto userLoginDto);
	boolean saveLikedRecipe(UserLikedRecipeDto recipeDto);
	Map<String, Object> getLikedRecipes(UserLikedRecipeDto recipeDto);
	Map<String, Object> removeLikedRecipe(UserLikedRecipeDto recipeDto);
}
