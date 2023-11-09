package com.example.news_service.mapper;

import com.example.news_service.model.News;
import com.example.news_service.service.NewsCategoryService;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.news.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {

  @Autowired
  private UserService userService;

  @Autowired
  private NewsCategoryService categoryService;

  @Override
  public News requestToNews( NewsRequest request ) {
    News news = new News();
    news.setText( request.getText() );
    news.setUser( userService.findById( request.getUserId() ) );
    news.setCategory( categoryService.findById( request.getCategoryId() ) );

    return news;
  }

  @Override
  public News requestToNews( Long newsId, NewsRequest request ) {
    News news = requestToNews( request );
    news.setId( newsId );

    return news;
  }
}
