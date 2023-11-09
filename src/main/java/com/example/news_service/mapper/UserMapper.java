package com.example.news_service.mapper;

import com.example.news_service.model.User;
import com.example.news_service.web.dto.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface UserMapper {

  User userRequestToUser( UserRequest userRequest );

  @Mapping(source = "userId", target = "id")
  User requestToUser( Long userId, UserRequest request );

  UserResponse userToResponse( User user );

  default UserListResponse userListToUserListResponse( List<User> users ) {
    UserListResponse response = new UserListResponse();

    response.setUsers( users.stream().map( this::userToResponse ).toList() );

    return response;
  }

  UserWithNewsResponse userWithNewsToResponse( User user );

  default UserWithNewsListResponse userWithNewsListToUserListResponse( List<User> users ) {
    UserWithNewsListResponse response = new UserWithNewsListResponse();

    response.setUsers( users.stream().map( this::userWithNewsToResponse ).toList() );

    return response;
  }

}
