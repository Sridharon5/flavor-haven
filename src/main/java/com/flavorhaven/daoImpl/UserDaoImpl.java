package com.flavorhaven.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.flavorhaven.dao.UserDao;
import com.flavorhaven.dto.UserLikedRecipeDto;
import com.flavorhaven.entity.UserEntity;
import com.flavorhaven.entity.UserLikedRecipeEntity;
import com.flavorhaven.repository.UserLikedRecipeRepository;
import com.flavorhaven.repository.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserLikedRecipeRepository userLikedRecipeRepository;

	@Override
	public UserEntity saveUser(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean saveLikedRecipe(UserLikedRecipeDto recipeDto) {
		Optional<UserEntity> userOpt = userRepository.findById(recipeDto.getUserId());

		if (userOpt.isEmpty()) {
			return false; // User not found
		}
		Optional<UserLikedRecipeEntity> existing = userLikedRecipeRepository
				.findByUserIdAndRecipeId(recipeDto.getUserId(), recipeDto.getRecipeId());

		if (existing.isPresent()) {
			return false;
		}

		UserLikedRecipeEntity likedRecipe = new UserLikedRecipeEntity();
		likedRecipe.setUser(userOpt.get());
		likedRecipe.setRecipeId(recipeDto.getRecipeId());
		likedRecipe.setRecipeImage(recipeDto.getRecipeImage());
		likedRecipe.setRecipeTitle(recipeDto.getRecipeTitle());

		userLikedRecipeRepository.save(likedRecipe);
		return true;
	}

	@Override
	public Map<String, Object> fetchLikedRecipesByUser(Long userId) {
		Optional<UserEntity> userOpt = userRepository.findById(userId);

		Map<String, Object> response = new HashMap<>();

		if (userOpt.isEmpty()) {
			response.put("results", new ArrayList<>());
			response.put("offset", 0);
			response.put("number", 0);
			response.put("totalResults", 0);
			return response;
		}

		List<UserLikedRecipeEntity> likedRecipes = userLikedRecipeRepository.findByUser(userOpt.get());

		List<Map<String, Object>> results = new ArrayList<>();

		for (UserLikedRecipeEntity recipe : likedRecipes) {
			Map<String, Object> recipeMap = new HashMap<>();
			recipeMap.put("id", recipe.getRecipeId());
			recipeMap.put("title", recipe.getRecipeTitle());
			recipeMap.put("image", recipe.getRecipeImage());
			recipeMap.put("imageType", "jpg");
			results.add(recipeMap);
		}

		response.put("results", results);
		response.put("offset", 0);
		response.put("number", results.size());
		response.put("totalResults", results.size());

		return response;
	}

	@Override
	public Map<String, Object> removeLikedRecipe(UserLikedRecipeDto recipeDto) {
		Map<String, Object> response = new HashMap<>();
		Optional<UserEntity> userOpt = userRepository.findById(recipeDto.getUserId());
		if (userOpt.isEmpty()) {
			response.put("status", "error");
			response.put("message", "User not found");
			return response;
		}
		Optional<UserLikedRecipeEntity> existing = userLikedRecipeRepository
				.findByUserIdAndRecipeId(recipeDto.getUserId(), recipeDto.getRecipeId());

		if (existing.isEmpty()) {
			response.put("status", "error");
			response.put("message", "Recipe not found in liked list");
			return response;
		}
		userLikedRecipeRepository.delete(existing.get());
		response.put("status", "success");
		response.put("message", "Recipe removed from liked list successfully");
		return response;
	}
}
