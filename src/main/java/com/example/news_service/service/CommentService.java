package com.example.news_service.service;

import com.example.news_service.model.Comment;
import com.example.news_service.model.User;
import com.example.news_service.web.dto.comment.AllCommentsForNewsRequest;

import java.util.List;

public interface CommentService {

  List<Comment> findAll( AllCommentsForNewsRequest pagesRequest );

  Comment findById( Long id );

  Comment save( Comment comment );

  Comment update( Long id, Comment comment );

  void deleteById( Long id );

}
