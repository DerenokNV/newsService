package com.example.news_service.web.dto.comment;

import com.example.news_service.web.dto.PagesRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AllCommentsForNewsRequest extends PagesRequest {

  @NotNull(message = "Для просмотра комментариев, необходимо указать ID новости")
  private Long newsId;

}
