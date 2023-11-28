package com.example.news_service.service.impl;

import com.example.news_service.model.Comment;
import com.example.news_service.model.News;
import com.example.news_service.repository.CommentRepository;
import com.example.news_service.security.AppUserPrincipal;
import com.example.news_service.security.facadeuser.IAuthenticationFacade;
import com.example.news_service.service.CommentService;
import com.example.news_service.service.NewsService;
import com.example.news_service.service.Toolkit;
import com.example.news_service.web.dto.comment.AllCommentsForNewsRequest;
import io.swagger.v3.oas.annotations.Operation;
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

  private final IAuthenticationFacade authenticationFacade;

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

    AppUserPrincipal principal = (AppUserPrincipal)authenticationFacade.getAuthentication().getPrincipal();
    comment.setUserComment( principal.getUser() );

    return commentRepository.save( comment );
  }

  @Operation(
          summary = "Создать комментарий + пользователя",
          description = "Создать комментарий + пользователя",
          tags = { "news", "user", "comment" }
  )
  @Override
  public Comment update( Long id, Comment comment ) {
    Comment updateComment = findById( id );
    updateComment.setText( comment.getText() );

    String resultCheck = Toolkit.checkUserPrincipalAndUserObject( Toolkit.TypeObject.COMMENT, Toolkit.TypeAction.UPDATE,
                                                                   updateComment.getUserComment().getId(), authenticationFacade );

    if ( resultCheck != null ) {
      throw new EntityNotFoundException( resultCheck );
    }

    return commentRepository.save( updateComment );
  }

  @Override
  public void deleteById( Long id ) {
    Comment deleteComment = findById( id );

    String resultCheck = Toolkit.checkUserPrincipalAndUserObject( Toolkit.TypeObject.COMMENT, Toolkit.TypeAction.UPDATE,
                                                                  deleteComment.getUserComment().getId(), authenticationFacade );

    if ( resultCheck != null ) {
      throw new EntityNotFoundException( resultCheck );
    }

    commentRepository.deleteById( id );
  }
}
