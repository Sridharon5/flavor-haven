package com.flavorhaven.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flavorhaven.dao.UserDao;
import com.flavorhaven.dto.UserLikedRecipeDto;
import com.flavorhaven.dto.UserLoginDto;
import com.flavorhaven.dto.UserSignupDto;
import com.flavorhaven.entity.UserEntity;
import com.flavorhaven.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public String signup(UserSignupDto userSignupDto) {

		Optional<UserEntity> existingUser = userDao.findByUsername(userSignupDto.getUsername());
		if (existingUser.isPresent()) {
			return "Username already exists!";
		}

		UserEntity user = new UserEntity();
		user.setName(userSignupDto.getName());
		user.setEmail(userSignupDto.getEmail());
		user.setUsername(userSignupDto.getUsername());
		user.setPassword(userSignupDto.getPassword());
		userDao.saveUser(user);

		return "User registered successfully!";
	}

	@Override
	public Map<String, Object> login(UserLoginDto userLoginDto) {
		Optional<UserEntity> user = userDao.findByUsername(userLoginDto.getUsername());
		Map<String, Object> response = new HashMap<>();

		if (user.isEmpty()) {
			response.put("status", "error");
			response.put("message", "Invalid username!");
			return response;
		}

		if (!user.get().getPassword().equals(userLoginDto.getPassword())) {
			response.put("status", "error");
			response.put("message", "Invalid password!");
			return response;
		}

		UserEntity userEntity = user.get();
		response.put("status", "success");
		response.put("message", "Login successful!");
		response.put("userId", userEntity.getId());
		response.put("username", userEntity.getUsername());

		return response;
	}

	@Override
	public boolean saveLikedRecipe(UserLikedRecipeDto recipeDto) {
		return userDao.saveLikedRecipe(recipeDto);
	}

	@Override
	public Map<String, Object> getLikedRecipes(UserLikedRecipeDto recipeDto) {
		return userDao.fetchLikedRecipesByUser(recipeDto.getUserId());
	}

	@Override
	public Map<String, Object> removeLikedRecipe(UserLikedRecipeDto recipeDto) {
		return userDao.removeLikedRecipe(recipeDto);
	}
}
