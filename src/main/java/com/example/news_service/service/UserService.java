package com.example.news_service.service;

import com.example.news_service.model.User;
import com.example.news_service.web.dto.PagesRequest;

import java.util.List;

public interface UserService {

  List<User> findAll( PagesRequest pagesRequest );

  User findById( Long id );

  User save( User user );

  User update( User user );

  void deleteById( Long id );

  List<User> findAllWithNews( PagesRequest pagesRequest );

  User findByFirstName( String firstName );
}
