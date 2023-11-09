package com.example.news_service.mapper;

import com.example.news_service.model.News;
import com.example.news_service.web.dto.news.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public interface NewsMapper {

  News requestToNews( NewsRequest request );

  @Mapping(source = "newsId", target = "id")
  News requestToNews( Long newsId, NewsRequest request );

  NewsResponse newsToResponse( News news );

  @Named(value = "commentMethod")
  NewsWithListCommentResponse newsToNewsCommentResponse( News news );

  default NewsListResponse newsListToNewsListResponse( List<News> news ) {
    NewsListResponse response = new NewsListResponse();
    List<NewsWithCountCommentResponse> results = new ArrayList<>();
    for ( News param : news ) {
      NewsWithCountCommentResponse result = new NewsWithCountCommentResponse( newsToResponse( param ), param.getComments().size() );
      results.add( result );
    }

    response.setNewsAll( results );

    return response;
  }
}
