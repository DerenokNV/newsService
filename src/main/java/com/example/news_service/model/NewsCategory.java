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
public class NewsCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  @ToString.Exclude
  @Builder.Default
  @Transient
  private List<News> newsList = new ArrayList<>();

}
