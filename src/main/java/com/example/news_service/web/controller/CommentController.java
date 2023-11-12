package com.example.news_service.web.controller;

import com.example.news_service.mapper.CommentMapper;
import com.example.news_service.mapper.UserMapper;
import com.example.news_service.model.Comment;
import com.example.news_service.service.CommentService;
import com.example.news_service.web.dto.comment.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
@Tag(name = "Комментарии к новостям", description = "Client API version V1, для управления сущностью Комментарии к новостям")
public class CommentController {

  private final CommentService service;

  private final CommentMapper mapper;

  private final UserMapper userMapper;

  @PostMapping
  public ResponseEntity<CommentResponse> create( @RequestBody @Valid CommentCreateRequest request ) {
    Comment newComment = service.save( mapper.commentCteateRequestToComment( request ) );

    return ResponseEntity.status( HttpStatus.CREATED )
            .body( mapper.commentToResponse( newComment ) );
  }

  @PostMapping("/withUser")
  public ResponseEntity<CommentResponse> createCommentWithUser( @RequestBody @Valid CommentWithUserRequest request ) {
    Comment newComment = service.saveWithUser( userMapper.userRequestToUser( request ), mapper.commentWithUserRequestToComment( request ), request.getNewsId() );

    return ResponseEntity.status( HttpStatus.CREATED )
            .body( mapper.commentToResponse( newComment ) );
  }

  @GetMapping
  public ResponseEntity<CommentListResponse> findAllByNews( @Valid AllCommentsForNewsRequest pagesRequest ) {
    return ResponseEntity.ok(
            mapper.commentListToCommentListResponse( service.findAll( pagesRequest ) )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentResponse> findById( @PathVariable Long id ) {
    return ResponseEntity.ok(
            mapper.commentToResponse( service.findById( id ) )
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<CommentResponse> update( @PathVariable Long id,
                                                 @RequestBody @Valid CommentUpdateRequest request ) {
    Comment currentComment = service.prepareUpdate( id, request.getText() );
    currentComment = service.update( currentComment, "ObjectUserId:" + currentComment.getUserComment().getId() );

    return ResponseEntity.ok( mapper.commentToResponse( currentComment ) );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById( @PathVariable Long id ) {
    Comment deleteComment = service.findById( id );
    service.deleteById( id, "ObjectUserId:" + deleteComment.getUserComment().getId() );

    return ResponseEntity.noContent().build();
  }

}
