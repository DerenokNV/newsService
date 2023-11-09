package com.example.news_service.service.impl;

import com.example.news_service.model.NewsCategory;
import com.example.news_service.repository.NewsCategoryRepository;
import com.example.news_service.service.NewsCategoryService;
import com.example.news_service.web.dto.PagesRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class PgNewsCategoryService implements NewsCategoryService {

  private final NewsCategoryRepository newsCategoryRepository;

  @Override
  public List<NewsCategory> findAll( PagesRequest pagesRequest ) {
    return newsCategoryRepository.findAll( PageRequest.of( pagesRequest.getPageNumber(), pagesRequest.getPageSize() ) ).getContent();
  }

  @Override
  public NewsCategory findById( Long id ) {
    return newsCategoryRepository.findById( id ).orElseThrow(
            () -> new EntityNotFoundException( MessageFormat.format( "Категория новостей с ID {0} не найдена!", id ) )
    );
  }

  @Override
  public NewsCategory save( NewsCategory newsCategory ) {
    return newsCategoryRepository.save( newsCategory );
  }

  @Override
  public NewsCategory update( NewsCategory newsCategory ) {
    return newsCategoryRepository.save( newsCategory );
  }

  @Override
  public void deleteById( Long id ) {
    newsCategoryRepository.deleteById( id );
  }
}
