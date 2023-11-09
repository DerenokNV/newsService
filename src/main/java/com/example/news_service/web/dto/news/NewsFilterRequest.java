package com.example.news_service.web.dto.news;

import com.example.news_service.web.dto.PagesRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewsFilterRequest extends PagesRequest {

  private Long categoryId;

  private Long userId;
}
