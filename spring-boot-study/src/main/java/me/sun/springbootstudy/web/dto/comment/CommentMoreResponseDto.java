package me.sun.springbootstudy.web.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.sun.springbootstudy.domain.comment.Comment;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CommentMoreResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime createDate;
    private Integer depth;
    private String name;

    /**
     * Service 단에서 추가하는 값들
     * 최상위 부모는 parendId는 null이다.
     * isMore은 계층별 조회시 자식들을 2개만 조회하고 2개보다 많을경우 클라이언트단에서 더보기 요청을 할수 있다.
     * 댓글의 자식들을 childrenResponseDto에 재귀적으로 담는다.
     */
    private Long parentId;
    private Boolean isMore;
    private List<CommentMoreResponseDto> childrenResponseDto;

    public CommentMoreResponseDto(Long commentId, String content, LocalDateTime createDate, Integer depth, Long parentId, String name) {
        this.commentId = commentId;
        this.content = content;
        this.createDate = createDate;
        this.depth = depth;
        this.parentId = parentId;
        this.name = name;
    }

    public CommentMoreResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getCreateDate();
        this.depth = comment.getDepth();
        this.name = comment.getMember().getName();
    }
}
