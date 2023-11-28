package com.example.news_service.web.controller;

import com.example.news_service.mapper.NewsCategoryMapper;
import com.example.news_service.model.NewsCategory;
import com.example.news_service.service.impl.PgNewsCategoryService;
import com.example.news_service.web.dto.PagesRequest;
import com.example.news_service.web.dto.newscategory.NewsCategoryListResponse;
import com.example.news_service.web.dto.newscategory.NewsCategoryRequest;
import com.example.news_service.web.dto.newscategory.NewsCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Tag(name = "Категории новостей", description = "Client API version V1, для управления сущностью Категория новостей")
public class NewsCategoryController {

  private final PgNewsCategoryService categoryService;

  private final NewsCategoryMapper mapper;

  @Operation(
          summary = "Список категорий",
          description = "Получить полный список категорий новостей",
          tags = { "category" }
  )
  @GetMapping
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsCategoryListResponse findAll( @Valid PagesRequest pagesRequest ) {
    return mapper.newsCategoryListToNewsCategoryListResponse( categoryService.findAll( pagesRequest ) );
  }

  @Operation(
          summary = "Категория по ее ID",
          description = "Получить категорию новости, по ее ID",
          tags = { "category" }
  )
  @GetMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsCategoryResponse findById( @PathVariable @Parameter(description = "ID категории новости") Long id ) {
    return mapper.newsCategoryToResponse( categoryService.findById( id ) );
  }

  @Operation(
          summary = "Создать категорию",
          description = "Создать категорию",
          tags = { "category" }
  )
  @PostMapping
  @PreAuthorize( "hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsCategoryResponse create( @RequestBody @Valid NewsCategoryRequest request ) {
    NewsCategory newNewsCategory = categoryService.save( mapper.requestToNewsCategory( request ) );

    return mapper.newsCategoryToResponse( newNewsCategory );
  }

  @Operation(
          summary = "Изменить категорию ",
          description = "Внести изменения в название Категории новостей",
          tags = { "category" }
  )
  @PutMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public NewsCategoryResponse update( @PathVariable @Parameter(description = "ID категории новости") Long id,
                                                      @RequestBody @Valid NewsCategoryRequest request ) {
    NewsCategory updateCategory = categoryService.update( mapper.requestToNewsCategory( id, request ) );

    return mapper.newsCategoryToResponse( updateCategory );
  }

  @Operation(
          summary = "Удалить категорию",
          description = "Удалить категорию",
          tags = { "category" }
  )
  @DeleteMapping("/{id}")
  @PreAuthorize( "hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')" )
  public void deleteById( @PathVariable @Parameter(description = "ID категории новости") Long id ) {
    categoryService.deleteById( id );
  }

}
