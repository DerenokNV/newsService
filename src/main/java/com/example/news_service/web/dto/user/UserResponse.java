package com.example.news_service.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;
}
