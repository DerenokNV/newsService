package com.example.news_service.web.dto;

import com.example.news_service.validation.PageFilterValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PageFilterValid
public class PagesRequest {

  private Integer pageSize;

  private Integer pageNumber;
}
