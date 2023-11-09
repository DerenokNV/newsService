package com.example.news_service.web.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsBasisResponse {

  private Long id;

  private String text;

}
