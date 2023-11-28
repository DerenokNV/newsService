package com.example.news_service.web.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {

  @NotBlank(message = "Комментарий не может быть пустым")
  private String text;

  @NotNull(message = "Комментарий создается к новости, укажите ID новости")
  private Long newsId;
}
