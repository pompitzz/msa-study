package me.sun.springbootstudy.web.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sun.springbootstudy.domain.board.Board;
import me.sun.springbootstudy.web.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Slice;

@NoArgsConstructor
@Getter
public class BoardAndCommentResponseDto {

    private String title;
    private String content;
    private Slice<CommentResponseDto> commentResponseDtoList;
    private String author;

    public BoardAndCommentResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getMember().getName();
    }

    public void setCommentResponseDtoList(Slice<CommentResponseDto> commentResponseDtoList) {
        this.commentResponseDtoList = commentResponseDtoList;
    }
}
