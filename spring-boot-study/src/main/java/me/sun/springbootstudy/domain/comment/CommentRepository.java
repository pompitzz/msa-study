package me.sun.springbootstudy.domain.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join fetch c.member where c.board.id = :boardId" +
            " and c.depth = 0",
            countQuery = "select count(c) from Comment c where c.board.id = :boardId and c.depth = 0")
    Slice<Comment> findTopPraent(@Param("boardId") Long boardId, Pageable pageable);


    @Query(value = "select c from Comment c join fetch c.member where c.parent.id = :parentId",
            countQuery = "select c from Comment c")
    Slice<Comment> findByParentId(@Param("parentId") Long parentId, Pageable pageable);

    @Query(value = "select c from Comment c join fetch c.member join fetch c.board where c.id = :id")
    Optional<Comment> findByIdWithMemberAndBoard(@Param("id") Long id);

}