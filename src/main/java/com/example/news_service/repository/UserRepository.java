package com.example.news_service.repository;

import com.example.news_service.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  @Override
  @EntityGraph(attributePaths = {"newsList"})
  List<User> findAll();

  @EntityGraph(attributePaths = {"roles"})
  Optional<User> findByFirstName( String firstName );

}
