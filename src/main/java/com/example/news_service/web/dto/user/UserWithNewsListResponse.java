package com.example.news_service.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithNewsListResponse {

  private List<UserWithNewsResponse> users = new ArrayList<>();

}
