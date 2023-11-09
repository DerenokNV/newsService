package com.example.news_service.web.dto.user;

import com.example.news_service.web.dto.news.NewsBasisResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithNewsResponse {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private List<NewsBasisResponse> newsList;
}
