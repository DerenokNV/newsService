package com.example.news_service.service;

import com.example.news_service.model.NewsCategory;
import com.example.news_service.web.dto.PagesRequest;

import java.util.List;

public interface NewsCategoryService {

  List<NewsCategory> findAll( PagesRequest pagesRequest );

  NewsCategory findById( Long id );

  NewsCategory save( NewsCategory newsCategory );

  NewsCategory update( NewsCategory newsCategory );

  void deleteById( Long id );

}
