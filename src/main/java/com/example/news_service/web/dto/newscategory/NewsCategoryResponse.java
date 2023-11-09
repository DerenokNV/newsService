package com.example.news_service.web.dto.newscategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategoryResponse {

  private Long id;

  private String description;

}
