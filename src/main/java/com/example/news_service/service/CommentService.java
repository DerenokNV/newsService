package com.example.news_service.service;

import com.example.news_service.model.Comment;
import com.example.news_service.web.dto.comment.AllCommentsForNewsRequest;

import java.util.List;

public interface CommentService {

  List<Comment> findAll( AllCommentsForNewsRequest pagesRequest );

  Comment findById( Long id );

  Comment save( Comment comment );

  Comment update( Comment comment, String paramNewsUserId );

  void deleteById( Long id, String paramNewsUserId );

}
