package com.example.news_service.web.dto.news;

import com.example.news_service.web.dto.newscategory.NewsCategoryResponse;
import com.example.news_service.web.dto.user.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewsResponse extends NewsBasisResponse {

  private NewsCategoryResponse category;

  private UserResponse user;

  public NewsResponse( Long id, String text, NewsCategoryResponse categoryResponse, UserResponse userResponse ) {
    super( id, text );
    this.category = categoryResponse;
    this.user = userResponse;
  }
}
