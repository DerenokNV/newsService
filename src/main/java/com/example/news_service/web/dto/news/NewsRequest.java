package com.example.news_service.web.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {

  @NotBlank(message = "Текст новости должен быть заполнен")
  private String text;

  @NotNull(message = "Должен быть указан пользователь создавший новость")
  private Long userId;

  @NotNull(message = "Должна быть указана категория для размещения новости")
  private Long categoryId;
}
