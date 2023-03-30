package com.gsc.cathelp.dao;

import com.gsc.cathelp.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByCatIdAndParentCommentNull(Long catId, Sort sort);
}
