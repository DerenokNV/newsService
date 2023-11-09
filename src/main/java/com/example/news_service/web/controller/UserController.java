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
  public ResponseEntity<UserListResponse> findAll( @Valid PagesRequest pagesRequest ) {
    return ResponseEntity.ok(
            userMapper.userListToUserListResponse( pgUserService.findAll( pagesRequest ) )
    );
  }

  @GetMapping("/withNews")
  public ResponseEntity<UserWithNewsListResponse> findAllWithNews( @Valid PagesRequest pagesRequest ) {
    return ResponseEntity.ok(
            userMapper.userWithNewsListToUserListResponse( pgUserService.findAll( pagesRequest ) )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> findById( @PathVariable Long id ) {
    return ResponseEntity.ok(
            userMapper.userToResponse( pgUserService.findById( id ) )
    );
  }

  @PostMapping
  public ResponseEntity<UserResponse> create( @RequestBody @Valid UserRequest request ) {
    User newUser = pgUserService.save( userMapper.userRequestToUser( request )  );

    return ResponseEntity.status( HttpStatus.CREATED )
                         .body( userMapper.userToResponse( newUser ) );
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update( @PathVariable("id") Long userId,
                                              @RequestBody @Valid UserRequest request ) {
    User updateUser = pgUserService.update( userMapper.requestToUser( userId, request ) );

    return ResponseEntity.ok( userMapper.userToResponse( updateUser ) );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete( @PathVariable Long id ) {
    pgUserService.deleteById( id );

    return ResponseEntity.noContent().build();
  }
}
