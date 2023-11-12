package com.example.news_service.service;

import com.example.news_service.model.News;
import com.example.news_service.model.User;
import com.example.news_service.web.dto.PagesRequest;
import com.example.news_service.web.dto.news.NewsFilterRequest;

import java.util.List;

public interface NewsService {

  List<News> filterBy( NewsFilterRequest filter );

  List<News> findAll( PagesRequest pagesRequest );

  News findById( Long id );

  News save( News news );

  News saveWithUser( User user, News news, Long categoryId );

  News update( News news, String paramNewsUserId );

  void deleteById( Long id, String paramNewsUserId );
}
