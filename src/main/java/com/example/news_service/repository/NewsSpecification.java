package com.example.news_service.repository;

import com.example.news_service.model.News;
import com.example.news_service.web.dto.news.NewsFilterRequest;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

  static Specification<News> withFilter( NewsFilterRequest newsFilter ) {
    return Specification.where( byCategoryId( newsFilter.getCategoryId() ) )
                        .and( byUserId( newsFilter.getUserId() ) );
  }

  static Specification<News> byCategoryId( Long categoryId ) {
    return ( root, query, cb ) -> {
      if ( categoryId == null ) {
        return null;
      }

      return cb.equal( root.get("category").get( "id" ), categoryId );
    };
  }

  static Specification<News> byUserId( Long userId ) {
    return ( root, query, cb ) -> {
      if ( userId == null ) {
        return null;
      }

      return cb.equal( root.get("user").get( "id" ), userId );
    };
  }

}
