package com.example.news_service.repository;

import com.example.news_service.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Page<Comment> findAllByNewsCommentId( Long newsId, Pageable pageable );

}
