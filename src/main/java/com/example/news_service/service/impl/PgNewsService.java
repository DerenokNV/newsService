package com.example.news_service.service.impl;

import com.example.news_service.model.News;
import com.example.news_service.model.NewsCategory;
import com.example.news_service.repository.NewsRepository;
import com.example.news_service.repository.NewsSpecification;
import com.example.news_service.security.AppUserPrincipal;
import com.example.news_service.security.facadeuser.IAuthenticationFacade;
import com.example.news_service.service.NewsCategoryService;
import com.example.news_service.service.NewsService;
import com.example.news_service.service.Toolkit;
import com.example.news_service.web.dto.PagesRequest;
import com.example.news_service.web.dto.news.NewsFilterRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class PgNewsService implements NewsService {

  private final NewsRepository repository;

  private final NewsCategoryService categoryService;

  private final IAuthenticationFacade authenticationFacade;

  @Override
  public List<News> filterBy( NewsFilterRequest filter ) {
    return repository.findAll(
            NewsSpecification.withFilter( filter ),
            PageRequest.of( filter.getPageNumber(), filter.getPageSize() )
    ).getContent();
  }

  @Override
  public List<News> findAll( PagesRequest pagesRequest ) {
    return repository.findAll( PageRequest.of( pagesRequest.getPageNumber(), pagesRequest.getPageSize() ) )
                                  .getContent();
  }

  @Override
  public News findById( Long id ) {
    log.error( "Запрашиваем ID новости: " );
    return repository.findById( id ).orElseThrow(
            () -> new EntityNotFoundException( MessageFormat.format( "Новость с ID {0} не найдена!", id ) )
    );
  }

  @Override
  public News save( News news, Principal principal ) {
    if ( !( principal instanceof UsernamePasswordAuthenticationToken ) || !( ( ( UsernamePasswordAuthenticationToken )principal ).getPrincipal() instanceof AppUserPrincipal )  ) {
      throw new EntityNotFoundException( "Никто не авторизирован " );
    }

    news.setUser( ( (AppUserPrincipal)( ( (UsernamePasswordAuthenticationToken)principal ).getPrincipal() ) ).getUser() );

    NewsCategory category = categoryService.findById( news.getCategory().getId() );
    news.setCategory( category );

    return repository.save( news );
  }

  @Override
  public News update( News news ) {
    News updateNews = findById( news.getId() );
    updateNews.setText( news.getText() );
    updateNews.setCategory( news.getCategory() );

    String resultCheck = Toolkit.checkUserPrincipalAndUserObject( Toolkit.TypeObject.NEWS, Toolkit.TypeAction.UPDATE,
                                                                  updateNews.getUser().getId(), authenticationFacade );

    if ( resultCheck != null ) {
      throw new EntityNotFoundException( resultCheck );
    }

    return repository.save( updateNews );
  }


  @Override
  public void deleteById( Long id ) {
    News deleteNews = findById( id );

    String resultCheck = Toolkit.checkUserPrincipalAndUserObject( Toolkit.TypeObject.NEWS, Toolkit.TypeAction.DELETE,
                                                                  deleteNews.getUser().getId(), authenticationFacade );

    if ( resultCheck != null ) {
      throw new EntityNotFoundException( resultCheck );
    }

    repository.deleteById( id );
  }
}
