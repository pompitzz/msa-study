package me.sun.springbootstudy.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update Board b set b.viewsCount = b.viewsCount + 1 where b.id = :id")
    void countViewsCount(@Param("id") Long id);


}
