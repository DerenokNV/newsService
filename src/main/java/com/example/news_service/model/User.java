package com.example.news_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  private String password;

  @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "roles", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<RoleType> roles = new HashSet<>();

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
