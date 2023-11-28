package com.example.news_service.web.controller;

import com.example.news_service.mapper.NewsMapper;
import com.example.news_service.model.News;
import com.example.news_service.service.NewsService;
import com.example.news_service.web.dto.PagesRequest;
import com.example.news_service.web.dto.news.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/news")
@AllArgsConstructor
@Tag(name = "Новости", description = "Client API version V1, для управления сущностью Новости")
public class NewsController {

  private final NewsService service;

  private final NewsMapper mapper;

  @GetMapping("/filter")
  public NewsListResponse filterBy( @Valid NewsFilterRequest filter ) {
    return  mapper.newsListToNewsListResponse( service.filterBy( filter ) );
  }

  @GetMapping
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsListResponse findAll( @Valid PagesRequest pagesRequest ) {
    return mapper.newsListToNewsListResponse( service.findAll( pagesRequest ) );
  }


  @GetMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsWithListCommentResponse findById( @PathVariable Long id ) {
    return mapper.newsToNewsCommentResponse( service.findById( id ) );
  }

  @PostMapping
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsResponse create( @RequestBody @Valid NewsRequest request,
                              Principal principal ) {
    News newNews = service.save( mapper.requestToNews( request ), principal );

    return mapper.newsToResponse( newNews );
  }


  @PutMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsResponse update( @PathVariable Long id,
                              @RequestBody @Valid NewsRequest request ) {
    News updateNews = service.update( mapper.requestToNews( id, request ) );

    return mapper.newsToResponse( updateNews );
  }

  @DeleteMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public void deleteById( @PathVariable Long id ) {
    News deleteNews = service.findById( id );
    service.deleteById( id );
  }
}
