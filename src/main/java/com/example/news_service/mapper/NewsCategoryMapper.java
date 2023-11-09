package com.example.news_service.mapper;

import com.example.news_service.model.NewsCategory;
import com.example.news_service.web.dto.newscategory.NewsCategoryListResponse;
import com.example.news_service.web.dto.newscategory.NewsCategoryRequest;
import com.example.news_service.web.dto.newscategory.NewsCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsCategoryMapper {

  NewsCategory requestToNewsCategory( NewsCategoryRequest newsCategoryRequest );

  @Mapping(source = "newCategoryId", target = "id")
  NewsCategory requestToNewsCategory( Long newCategoryId, NewsCategoryRequest request );

  NewsCategoryResponse newsCategoryToResponse( NewsCategory newsCategory );

  default NewsCategoryListResponse newsCategoryListToNewsCategoryListResponse( List<NewsCategory> newsCategories ) {
    NewsCategoryListResponse response = new NewsCategoryListResponse();

    response.setNewsCategoryAll( newsCategories.stream().map( this::newsCategoryToResponse ).toList() );

    return response;
  }
}
