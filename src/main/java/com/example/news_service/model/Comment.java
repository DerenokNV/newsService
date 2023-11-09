package com.example.news_service.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;

  @ManyToOne
  @JoinColumn(name = "news_id")
  @ToString.Exclude
  private News newsComment;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private User userComment;
}
