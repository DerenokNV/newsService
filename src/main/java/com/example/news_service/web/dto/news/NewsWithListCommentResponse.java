package com.example.news_service.web.dto.news;

import com.example.news_service.web.dto.comment.CommentListResponse;
import com.example.news_service.web.dto.newscategory.NewsCategoryResponse;
import com.example.news_service.web.dto.user.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewsWithListCommentResponse extends NewsResponse {

  private CommentListResponse comments;

  public NewsWithListCommentResponse( Long id, String text, NewsCategoryResponse categoryResponse, UserResponse userResponse, CommentListResponse comments ) {
    super( id, text, categoryResponse, userResponse );
    this.comments = comments;
  }

  public NewsWithListCommentResponse( NewsResponse response, CommentListResponse comments ) {
    this( response.getId(), response.getText(), response.getCategory(), response.getUser(), comments );
  }
}
