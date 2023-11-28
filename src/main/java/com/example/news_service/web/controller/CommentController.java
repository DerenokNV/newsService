package com.example.news_service.web.controller;

import com.example.news_service.mapper.CommentMapper;
import com.example.news_service.model.Comment;
import com.example.news_service.service.CommentService;
import com.example.news_service.web.dto.comment.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
@Tag(name = "Комментарии к новостям", description = "Client API version V1, для управления сущностью Комментарии к новостям")
public class CommentController {

  private final CommentService service;

  private final CommentMapper mapper;


  @PostMapping
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public CommentResponse create( @RequestBody @Valid CommentCreateRequest request ) {
    Comment newComment = service.save( mapper.commentCteateRequestToComment( request ) );

    return mapper.commentToResponse( newComment );
  }

  @GetMapping
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public CommentListResponse findAllByNews( @Valid AllCommentsForNewsRequest pagesRequest ) {
    return mapper.commentListToCommentListResponse( service.findAll( pagesRequest ) );
  }

  @GetMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public CommentResponse findById( @PathVariable Long id ) {
    return mapper.commentToResponse( service.findById( id ) );
  }

  @PutMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public CommentResponse update( @PathVariable Long id,
                                 @RequestBody @Valid CommentUpdateRequest request ) {
    return mapper.commentToResponse( service.update( id, mapper.commentUpdateRequestToComment( request )  ) );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public void deleteById( @PathVariable Long id ) {
    service.deleteById( id );
  }

}
