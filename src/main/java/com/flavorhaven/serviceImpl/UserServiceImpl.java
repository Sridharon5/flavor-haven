package com.flavorhaven.serviceImpl;

import com.flavorhaven.dao.UserDao;
import com.flavorhaven.dto.UserSignupDto;
import com.flavorhaven.dto.UserLoginDto;
import com.flavorhaven.entity.UserEntity;
import com.flavorhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public String login(UserLoginDto userLoginDto) {
        Optional<UserEntity> user = userDao.findByUsername(userLoginDto.getUsername());

        if (user.isEmpty()) {
            return "Invalid username!";
        }

        if (!user.get().getPassword().equals(userLoginDto.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }
}
