package com.example.news_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private User user;

  @ManyToOne
  @JoinColumn(name = "category_id")
  @ToString.Exclude
  private NewsCategory category;

  @OneToMany(mappedBy = "newsComment", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Builder.Default
  private List<Comment> comments = new ArrayList<>();

  private String text;
}
