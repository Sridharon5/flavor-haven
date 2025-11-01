package com.flavorhaven.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flavorhaven.dto.UserLikedRecipeDto;
import com.flavorhaven.dto.UserLoginDto;
import com.flavorhaven.dto.UserSignupDto;
import com.flavorhaven.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*") 
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody UserSignupDto signupDto) {
        String message = userService.signup(signupDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto loginDto) {
        Map<String, Object> response=userService.login(loginDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/recipeLiked")
    public String likeRecipe(@RequestBody UserLikedRecipeDto recipeDto) {
        boolean saved = userService.saveLikedRecipe(recipeDto);
        return saved ? "Recipe liked successfully!" : "User not found!";
    }
    @PostMapping("/getLikedRecipes")
    public Map<String, Object> getLikedRecipes(@RequestBody UserLikedRecipeDto recipeDto) {
        return userService.getLikedRecipes(recipeDto);
    }
    @PostMapping("/removeLikedRecipe")
    public Map<String, Object> removeLikedRecipes(@RequestBody UserLikedRecipeDto recipeDto) {
        return userService.removeLikedRecipe(recipeDto);
    }
    
}
