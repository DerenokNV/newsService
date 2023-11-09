package com.example.news_service.service.impl;

import com.example.news_service.aop.UserVerification;
import com.example.news_service.model.News;
import com.example.news_service.model.NewsCategory;
import com.example.news_service.model.User;
import com.example.news_service.repository.NewsRepository;
import com.example.news_service.repository.NewsSpecification;
import com.example.news_service.service.NewsCategoryService;
import com.example.news_service.service.NewsService;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.PagesRequest;
import com.example.news_service.web.dto.news.NewsFilterRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class PgNewsService implements NewsService {

  private final NewsRepository repository;

  private final UserService userService;

  private final NewsCategoryService categoryService;

  @Override
  public List<News> filterBy( NewsFilterRequest filter ) {
    return repository.findAll(
            NewsSpecification.withFilter( filter ),
            PageRequest.of( filter.getPageNumber(), filter.getPageSize() )
    ).getContent();
  }

  @Override
  public List<News> findAll( PagesRequest pagesRequest ) {
    List<News> result = repository.findAll( PageRequest.of( pagesRequest.getPageNumber(), pagesRequest.getPageSize() ) )
                                  .getContent();

    return result;
  }

  @Override
  public News findById( Long id ) {
    return repository.findById( id ).orElseThrow(
            () -> new EntityNotFoundException( MessageFormat.format( "Новость с ID {0} не найдена!", id ) )
    );
  }

  @Override
  public News save( News news ) {
    User user = userService.findById( news.getUser().getId() );
    news.setUser( user );

    NewsCategory category = categoryService.findById( news.getCategory().getId() );
    news.setCategory( category );

    return repository.save( news );
  }

  @Override
  @UserVerification
  public News update( News news, String paramNewsUserId ) {
    return repository.save( news );
  }

  @Override
  @UserVerification
  public void deleteById( Long id, String paramNewsUserId ) {
    repository.deleteById( id );
  }
}
