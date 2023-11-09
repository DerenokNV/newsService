package com.example.news_service.mapper;

import com.example.news_service.model.Comment;
import com.example.news_service.web.dto.comment.CommentCreateRequest;
import com.example.news_service.web.dto.comment.CommentListResponse;
import com.example.news_service.web.dto.comment.CommentResponse;
import com.example.news_service.web.dto.comment.CommentUpdateRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

  Comment commentCteateRequestToComment( CommentCreateRequest commentCreateRequest );

  @Mapping(source = "commentId", target = "id")
  Comment requestToComment( Long commentId, CommentUpdateRequest commentRequest );

  CommentResponse commentToResponse( Comment comment );

  default CommentListResponse commentListToCommentListResponse( List<Comment> comments ) {
    CommentListResponse response = new CommentListResponse();

    response.setComments( comments.stream().map( this::commentToResponse ).toList() );

    return response;
  }

}
