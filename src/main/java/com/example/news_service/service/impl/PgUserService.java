package com.example.news_service.service.impl;

import com.example.news_service.aop.UserVerification;
import com.example.news_service.model.User;
import com.example.news_service.repository.UserRepository;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.PagesRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PgUserService implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAll( PagesRequest pagesRequest ) {
    return userRepository.findAll( PageRequest.of( pagesRequest.getPageNumber(), pagesRequest.getPageSize() ) )
                         .getContent();
  }

  @Override
  @UserVerification
  public User findById( Long id ) {
    return userRepository.findById( id )
                         .orElseThrow(
                           () -> new EntityNotFoundException( MessageFormat.format("Пользователь с ID {0} не найден!", id ) )
                         );
  }

  @Override
  public User save( User user ) {
    user.setPassword( passwordEncoder.encode( user.getPassword() ) );

    return userRepository.saveAndFlush( user );
  }

  @Override
  @UserVerification
  public User update( User user ) {
    return save( user );
  }

  @Override
  @UserVerification
  public void deleteById( Long id ) {
    userRepository.deleteById( id );
  }

  @Override
  public List<User> findAllWithNews( PagesRequest pagesRequest ) {
    return findAll( pagesRequest );
  }

  @Override
  public User findByFirstName( String firstName ) {
    return userRepository.findByFirstName( firstName )
            .orElseThrow( () -> new RuntimeException("Username not found!") );
  }
}
