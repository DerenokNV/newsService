package com.example.news_service.web.controller;

import com.example.news_service.mapper.NewsMapper;
import com.example.news_service.mapper.UserMapper;
import com.example.news_service.model.News;
import com.example.news_service.service.NewsService;
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

  private final UserMapper userMapper;

  @GetMapping("/filter")
  public NewsListResponse filterBy( @Valid NewsFilterRequest filter ) {
    return  mapper.newsListToNewsListResponse( service.filterBy( filter ) );
  }

  @GetMapping
  public NewsListResponse findAll( @Valid PagesRequest pagesRequest ) {
    return mapper.newsListToNewsListResponse( service.findAll( pagesRequest ) );
  }


  @GetMapping("/{id}")
  public NewsWithListCommentResponse findById( @PathVariable Long id ) {
    return mapper.newsToNewsCommentResponse( service.findById( id ) );
  }

  @PostMapping
  public NewsResponse create( @RequestBody @Valid NewsRequest request ) {
    News newNews = service.save( mapper.requestToNews( request ) );

    return mapper.newsToResponse( newNews );
  }

  @PostMapping("/newsWithUser")
  public NewsResponse createNewsWithUser( @RequestBody @Valid NewsWithUserRequest request ) {
    return mapper.newsToResponse(
                    service.saveWithUser( userMapper.userRequestToUser( request ), mapper.newsWithUserRequestToNews( request ), request.getCategoryId() )
                 );
  }


  @PutMapping("/{id}")
  public NewsResponse update( @PathVariable Long id,
                                              @RequestBody @Valid NewsRequest request ) {
    News updateNews = service.update( mapper.requestToNews( id, request ), "ObjectUserId:" + request.getUserId() );

    return mapper.newsToResponse( updateNews );
  }

  @DeleteMapping("/{id}")
  public void deleteById( @PathVariable Long id ) {
    News deleteNews = service.findById( id );
    service.deleteById( id, "ObjectUserId:" + deleteNews.getUser().getId() );
  }
}
