package com.example.news_service.web.controller;

import com.example.news_service.mapper.NewsMapper;
import com.example.news_service.mapper.UserMapper;
import com.example.news_service.model.News;
import com.example.news_service.model.User;
import com.example.news_service.service.NewsService;
import com.example.news_service.service.UserService;
import com.example.news_service.web.dto.PagesRequest;
import com.example.news_service.web.dto.news.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@AllArgsConstructor
@Tag(name = "Новости", description = "Client API version V1, для управления сущностью Новости")
public class NewsController {

  private final NewsService service;

  private final NewsMapper mapper;

  private final UserService userService;

  private final UserMapper userMapper;

  @GetMapping("/filter")
  public ResponseEntity<NewsListResponse> filterBy( @Valid NewsFilterRequest filter ) {
    return  ResponseEntity.ok(
            mapper.newsListToNewsListResponse( service.filterBy( filter ) )
    );
  }

  @GetMapping
  public ResponseEntity<NewsListResponse> findAll( @Valid PagesRequest pagesRequest ) {
    return ResponseEntity.ok(
            mapper.newsListToNewsListResponse( service.findAll( pagesRequest ) )
    );
  }


  @GetMapping("/{id}")
  public ResponseEntity<NewsWithListCommentResponse> findById( @PathVariable Long id ) {
    return ResponseEntity.ok(
            mapper.newsToNewsCommentResponse( service.findById( id ) )
    );
  }

  @PostMapping
  public ResponseEntity<NewsResponse> create( @RequestBody @Valid NewsRequest request ) {
    News newNews = service.save( mapper.requestToNews( request ) );

    return ResponseEntity.status( HttpStatus.CREATED )
                         .body( mapper.newsToResponse( newNews ) );
  }

  @PostMapping("/newsWithUser")
  public ResponseEntity<NewsResponse> createNewsWithUser( @RequestBody @Valid NewsWithUserRequest request ) {
    User newUser = userService.save( userMapper.userRequestToUser( request ) );
    NewsRequest newsRequest = new NewsRequest( request.getText(), newUser.getId(), request.getCategoryId() );
    News newNews = service.save( mapper.requestToNews( newsRequest ) );

    return ResponseEntity.status( HttpStatus.CREATED )
            .body( mapper.newsToResponse( newNews ) );
  }


  @PutMapping("/{id}")
  public ResponseEntity<NewsResponse> update( @PathVariable Long id,
                                              @RequestBody @Valid NewsRequest request ) {
    News updateNews = service.update( mapper.requestToNews( id, request ), "ObjectUserId:" + request.getUserId() );

    return ResponseEntity.ok( mapper.newsToResponse( updateNews ) );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById( @PathVariable Long id ) {
    News deleteNews = service.findById( id );
    service.deleteById( id, "ObjectUserId:" + deleteNews.getUser().getId() );

    return ResponseEntity.noContent().build();
  }
}
