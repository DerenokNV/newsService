package com.example.news_service.service.impl;

import com.example.news_service.aop.UserVerification;
import com.example.news_service.model.Comment;
import com.example.news_service.model.News;
import com.example.news_service.model.User;
import com.example.news_service.repository.CommentRepository;
import com.example.news_service.service.CommentService;
import com.example.news_service.service.NewsService;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.comment.AllCommentsForNewsRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class PgCommentService implements CommentService {

  private final CommentRepository commentRepository;

  private final NewsService newsService;

  private final UserService userService;

  @Override
  public List<Comment> findAll( AllCommentsForNewsRequest pagesRequest ) {
    return commentRepository.findAllByNewsCommentId( pagesRequest.getNewsId(), PageRequest.of( pagesRequest.getPageNumber(), pagesRequest.getPageSize() ) )
                            .getContent();
  }

  @Override
  public Comment findById( Long id ) {
    return commentRepository.findById( id ).orElseThrow(
            () -> new EntityNotFoundException( MessageFormat.format( "Комментарий с ID {0} не найдена!", id ) )
    );
  }

  @Override
  public Comment save( Comment comment ) {
    News news = newsService.findById( comment.getNewsComment().getId() );
    comment.setNewsComment( news );

    User user = userService.findById( comment.getUserComment().getId() );
    comment.setUserComment( user );

    return commentRepository.save( comment );
  }

  @Override
  @UserVerification
  public Comment update( Comment comment, String paramNewsUserId ) {
    return commentRepository.save( comment );
  }

  @Override
  @UserVerification
  public void deleteById( Long id, String paramNewsUserId ) {
    commentRepository.deleteById( id );
  }
}
