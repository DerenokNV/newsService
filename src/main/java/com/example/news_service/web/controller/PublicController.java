package com.example.news_service.web.controller;

import com.example.news_service.mapper.UserMapper;
import com.example.news_service.model.User;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.user.UserRequest;
import com.example.news_service.web.dto.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

  private final UserService pgUserService;

  private final UserMapper userMapper;

  @PostMapping("/create-user")
  public UserResponse createUser( @RequestBody @Valid UserRequest request ) {
    User newUser = pgUserService.save( userMapper.userRequestToUser( request )  );

    return userMapper.userToResponse( newUser );
  }

}
