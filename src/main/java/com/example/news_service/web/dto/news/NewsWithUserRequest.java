package com.example.news_service.web.dto.news;

import com.example.news_service.web.dto.user.UserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsWithUserRequest extends UserRequest {

  @NotBlank(message = "Текст новости должен быть заполнен")
  private String text;

  @NotNull(message = "Должна быть указана категория для размещения новости")
  private Long categoryId;

}
