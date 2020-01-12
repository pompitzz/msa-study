package me.sun.springbootstudy.domain.comment;

import lombok.RequiredArgsConstructor;
import me.sun.springbootstudy.web.dto.comment.CommentResponseDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class CommentRepositodyCustom {

    private final EntityManager em;

    public List<CommentResponseDto> findChildrenByparentIds(List<Long> parentIds) {
        return em.createQuery(
                "select new me.sun.springbootstudy.web.dto.CommentResponseDto" +
                        " (c.id, c.content, c.createDate, c.depth, c.parent.id, m.name)" +
                        " from Comment c" +
                        " join c.member m" +
                        " where c.parent.id in :parentIds", CommentResponseDto.class)
                .setParameter("parentIds", parentIds)
                .getResultList();
    }
}
