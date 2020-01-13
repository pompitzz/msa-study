package me.sun.springbootstudy.web.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.web.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardAndCommentResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String email;
    private LocalDateTime lastModifiedDate;
    private Slice<CommentResponseDto> commentResponseDtoList;

    public BoardAndCommentResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getMember().getName();
        this.lastModifiedDate = board.getLastModifiedDate();
        this.email = board.getMember().getEmail();
    }

    public void setCommentResponseDtoList(Slice<CommentResponseDto> commentResponseDtoList) {
        this.commentResponseDtoList = commentResponseDtoList;
    }
}
