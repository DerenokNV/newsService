package com.example.news_service.web.dto.user;

import com.example.news_service.model.RoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

  @NotBlank(message = "Имя клиента должно быть заполнено!")
  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private Set<RoleType> roles;

}
