package com.example.news_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Builder.Default
  private List<News> newsList = new ArrayList<>();

  @OneToMany(mappedBy = "userComment", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Builder.Default
  private List<Comment> comments = new ArrayList<>();

  public void addNewsList( News news ) {
    newsList.add( news );
  }

  public void removeNewsList( Long newsId ) {
    newsList = newsList.stream().filter( x -> !x.getId().equals( newsId ) ).toList();
  }
}
