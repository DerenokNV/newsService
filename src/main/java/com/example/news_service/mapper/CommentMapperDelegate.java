package com.example.news_service.mapper;

import com.example.news_service.model.Comment;
import com.example.news_service.service.NewsService;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.comment.CommentCreateRequest;
import com.example.news_service.web.dto.comment.CommentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper{

  @Autowired
  private NewsService newsService;

  @Autowired
  private UserService userService;

  @Override
  public Comment commentCteateRequestToComment( CommentCreateRequest commentCreateRequest ) {
    Comment comment = new Comment();
    comment.setText( commentCreateRequest.getText() );
    comment.setNewsComment( newsService.findById( commentCreateRequest.getNewsId() ) );

    return comment;
  }

  @Override
  public Comment requestToComment( Long commentId, CommentUpdateRequest commentRequest ) {
    Comment comment = new Comment();
    comment.setText( commentRequest.getText() );
    comment.setId( commentId );

    return comment;
  }
}
