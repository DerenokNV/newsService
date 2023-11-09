package com.example.news_service.web.dto.comment;

import com.example.news_service.web.dto.news.NewsResponse;
import com.example.news_service.web.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

  private Long id;

  private String text;

  private NewsResponse newsComment;

  private UserResponse userComment;

}
