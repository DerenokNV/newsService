package com.example.news_service.service.impl;

import com.example.news_service.model.User;
import com.example.news_service.repository.UserRepository;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.PagesRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PgUserService implements UserService {

  private final UserRepository userRepository;

  @Override
  public List<User> findAll( PagesRequest pagesRequest ) {
    return userRepository.findAll( PageRequest.of( pagesRequest.getPageNumber(), pagesRequest.getPageSize() ) )
                         .getContent();
  }

  @Override
  public User findById( Long id ) {
    return userRepository.findById( id )
                         .orElseThrow(
                           () -> new EntityNotFoundException( MessageFormat.format("Пользователь с ID {0} не найден!", id ) )
                         );
  }

  @Override
  public User save( User user ) {
    return userRepository.save( user );
  }

  @Override
  public User update( User user ) {
    return userRepository.save( user );
  }

  @Override
  public void deleteById( Long id ) {
    userRepository.deleteById( id );
  }

  @Override
  public List<User> findAllWithNews( PagesRequest pagesRequest ) {
    return findAll( pagesRequest );
  }
}
