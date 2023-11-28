package com.example.news_service.service;

import com.example.news_service.model.News;
import com.example.news_service.web.dto.PagesRequest;
import com.example.news_service.web.dto.news.NewsFilterRequest;

import java.security.Principal;
import java.util.List;

public interface NewsService {

  List<News> filterBy( NewsFilterRequest filter );

  List<News> findAll( PagesRequest pagesRequest );

  News findById( Long id );

  News save( News news, Principal principal );

  News update( News news );

  void deleteById( Long id );
}
