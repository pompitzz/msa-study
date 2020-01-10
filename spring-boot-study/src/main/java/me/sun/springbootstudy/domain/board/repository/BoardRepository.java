package me.sun.springbootstudy.domain.board.repository;

import me.sun.springbootstudy.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update Board b set b.viewsCount = b.viewsCount + 1 where b.id = :id")
    void countViewsCount(@Param("id") Long id);

    @Query(value = "select b from Board b where b.title like concat('%', :title, '%')")
    List<Board> findBoardsTitle(@Param("title") String title);

    @Query(value = "select b from Board b where b.title like concat('%', :title, '%')")
    Page<Board> findBoardsTitleWithPageable(@Param("title") String title, Pageable pageable);


}
