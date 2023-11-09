package com.example.news_service.web.dto.newscategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность Категории новости")
public class NewsCategoryRequest {

  @NotBlank(message = "Название категории не может быть пустым")
  @Schema(description = "Описание категории")
  private String description;
}
