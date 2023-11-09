package com.example.news_service.web.dto.news;

import com.example.news_service.web.dto.newscategory.NewsCategoryResponse;
import com.example.news_service.web.dto.user.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewsWithCountCommentResponse extends NewsResponse{

  private int countComment;

  public NewsWithCountCommentResponse( Long id, String text, NewsCategoryResponse categoryResponse, UserResponse userResponse, int countComment ) {
    super( id, text, categoryResponse, userResponse );
    this.countComment = countComment;
  }

  public NewsWithCountCommentResponse( NewsResponse response, int countComment ) {
    this( response.getId(), response.getText(), response.getCategory(), response.getUser(), countComment );
  }
}
