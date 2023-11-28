package com.example.news_service.web.dto.comment;

import com.example.news_service.web.dto.user.UserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CommentWithUserRequest {

  @NotBlank(message = "Комментарий не может быть пустым")
  private String text;

  @NotNull(message = "Комментарий создается к новости, укажите ID новости")
  private Long newsId;
}
