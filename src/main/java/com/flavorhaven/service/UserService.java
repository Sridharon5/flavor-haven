package com.flavorhaven.service;

import com.flavorhaven.dto.UserSignupDto;
import com.flavorhaven.dto.UserLoginDto;
import com.flavorhaven.entity.UserEntity;

public interface UserService {
    String signup(UserSignupDto userSignupDto);
    String login(UserLoginDto userLoginDto);
}
