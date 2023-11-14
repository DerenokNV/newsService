package com.example.news_service.web.controller;

import com.example.news_service.mapper.UserMapper;
import com.example.news_service.model.User;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.user.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.news_service.web.dto.PagesRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Client API version V1, для управления сущностью Пользователь")
public class UserController {

  private final UserService pgUserService;

  private final UserMapper userMapper;

  @GetMapping
  public UserListResponse findAll( @Valid PagesRequest pagesRequest ) {
    return userMapper.userListToUserListResponse( pgUserService.findAll( pagesRequest ) );
  }

  @GetMapping("/withNews")
  public UserWithNewsListResponse findAllWithNews( @Valid PagesRequest pagesRequest ) {
    return userMapper.userWithNewsListToUserListResponse( pgUserService.findAll( pagesRequest ) );
  }

  @GetMapping("/{id}")
  public UserResponse findById( @PathVariable Long id ) {
    return userMapper.userToResponse( pgUserService.findById( id ) );
  }

  @PostMapping
  public UserResponse create( @RequestBody @Valid UserRequest request ) {
    User newUser = pgUserService.save( userMapper.userRequestToUser( request )  );

    return userMapper.userToResponse( newUser );
  }

  @PutMapping("/{id}")
  public UserResponse update( @PathVariable("id") Long userId,
                                              @RequestBody @Valid UserRequest request ) {
    User updateUser = pgUserService.update( userMapper.requestToUser( userId, request ) );

    return userMapper.userToResponse( updateUser );
  }

  @DeleteMapping("/{id}")
  public void delete( @PathVariable Long id ) {
    pgUserService.deleteById( id );
  }
}
