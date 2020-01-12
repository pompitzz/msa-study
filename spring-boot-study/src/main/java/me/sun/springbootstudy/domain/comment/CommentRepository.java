package me.sun.springbootstudy.domain.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join fetch c.member where c.board.id = :boardId" +
            " and c.depth = 0",
            countQuery = "select count(c) from Comment c where c.board.id = :boardId and c.depth = 0")
    Slice<Comment> findTopPraent(@Param("boardId") Long boardId, Pageable pageable);


    @Query(value = "select c from Comment c where c.parent.id = :id")
    Slice<Comment> findDepth0ByPranID(@Param("id") Long id, Pageable pageable);


}
