package me.sun.springbootstudy.domain.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Slice<Comment> findDepth0ByBoardId(@Param("boardId") Long boardId, Pageable pageable);


    @Query(value = "select c from Comment c where c.parent.id = :id")
    Slice<Comment> findDepth0ByPranID(@Param("id") Long id, Pageable pageable);
}
