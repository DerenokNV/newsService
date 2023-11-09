package com.example.news_service.web.dto.newscategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategoryListResponse {

  private List<NewsCategoryResponse> newsCategoryAll;
}
